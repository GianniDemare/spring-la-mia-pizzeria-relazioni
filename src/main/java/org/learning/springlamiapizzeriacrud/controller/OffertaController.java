package org.learning.springlamiapizzeriacrud.controller;

import jakarta.validation.Valid;
import org.learning.springlamiapizzeriacrud.model.Offerta;
import org.learning.springlamiapizzeriacrud.model.Pizza;
import org.learning.springlamiapizzeriacrud.repository.OffertaRepository;
import org.learning.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/offerta")
public class OffertaController {
    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private OffertaRepository offertaRepository;

    @GetMapping("/create")
    public String offer(@RequestParam(name = "pizzaId", required = true) Integer pizzaId, Model model) {
        Optional<Pizza> result = pizzaRepository.findById(pizzaId);
        if (result.isPresent()) {
            Pizza pizza = result.get();
            model.addAttribute("pizza", pizza);
            Offerta newOffer = new Offerta();
            newOffer.setPizza(pizza);
            model.addAttribute("offerta", newOffer);
            return "offerta/createOffert";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Pizza with id " + pizzaId + " not found");
        }
    }

    @PostMapping("/create")
    public String menu(@Valid @ModelAttribute("offerta") Offerta formOfferta, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("pizza", formOfferta.getPizza());
            return "offerta/createOffert";
        }
        if (formOfferta.getExpireDate() != null && formOfferta.getExpireDate().isBefore(formOfferta.getStartDate())) {
            formOfferta.setExpireDate(formOfferta.getStartDate());
        }
        Offerta storedOffer = offertaRepository.save(formOfferta);
        return "redirect:/pizze/show/" + storedOffer.getPizza().getId();
    }



    @GetMapping("/edit/{id}")
    public String editOffer(@PathVariable int id, Model model) {
        Optional<Offerta> offertaRecovery = offertaRepository.findById(id);
        Offerta offerta = offertaRecovery.get();
        model.addAttribute("offerta", offerta);
        return "offerta/editOffert";
    }

    @PostMapping("/edit/{id}")
    public String saveEditOffer(@PathVariable int id, Model model, @Valid @ModelAttribute("offerta") Offerta editOffert) {

        Optional<Offerta> offertaRecovery = offertaRepository.findById(id);
        Offerta offerta = offertaRecovery.get();
        offertaRepository.save(editOffert);
        return "redirect:/pizze/show/" + editOffert.getPizza().getName();
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Optional<Offerta> result = offertaRepository.findById(id);
        if (result.isPresent()) {
            Offerta offertaToDelete = result.get();
            offertaRepository.delete(offertaToDelete);
            return "redirect:/pizze/show/" + offertaToDelete.getPizza().getId();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "L'offerta con id " + id + " non è stata trovata");
        }
    }


}


