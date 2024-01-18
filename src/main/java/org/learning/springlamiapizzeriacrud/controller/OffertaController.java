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
    // IL CONTROLLER HA BISOGNO DELLE FUNZIONALITA DEL REPOSITORY
    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private OffertaRepository offertaRepository;

    // METODO PER MOSTRARE LA PAGINA COL FORM DI CREAZIONE DI UN OFFERTA
    @GetMapping("/create")
    public String offer(@RequestParam(name = "pizzaId", required = true) Integer pizzaId, Model model) {
        // VERIFICO SE LA PIZZA ESISTE SUL DATABASE
        Optional<Pizza> result = pizzaRepository.findById(pizzaId);
        if (result.isPresent()) {
            // ESTRAGGO LA PIZZA DALL'OPTIONAL
            Pizza pizza = result.get();
            // PASSO AL MODEL LA PIZZA COME ATTRIBUTO
            model.addAttribute("pizza", pizza);
            // PREPARO L'OFFERTA PER PRECARICARE IL FORM
            Offerta newOffer = new Offerta();
            newOffer.setPizza(pizza);
            model.addAttribute("offerta", newOffer);
            // RESTITUISCO IL TEMPLATE
            return "offerta/createOffert";
        } else {
            // SE L'OPTIONAL E' VUOTO SOLLEVO UN ECCEZIONE HTTP 404
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Pizza with id " + pizzaId + " not found");
        }
    }

    @PostMapping("/create")
    public String menu(@Valid @ModelAttribute("offerta") Offerta formOfferta, BindingResult bindingResult, Model model) {
        // VALIDO L'OGGETTO
        if (bindingResult.hasErrors()) {
            // SE CI SONO ERRORI RITORNO IL TEMPLATE DEL FORM
            model.addAttribute("pizza", formOfferta.getPizza());
            return "offerta/createOffert";
        }
        if (formOfferta.getExpireDate() != null && formOfferta.getExpireDate().isBefore(formOfferta.getStartDate())) {
            formOfferta.setExpireDate(formOfferta.getStartDate());
        }
        // SE NON CI SONO ERRORI LO SALVO SUL DATABASE
        Offerta storedOffer = offertaRepository.save(formOfferta);
        return "redirect:/pizze/show/" + storedOffer.getPizza().getId();
    }


    // METODO PER RESTITUIRE LA PAGINA COL FORM DI MODIFICA
    @GetMapping("/edit/{id}")
    public String editOffer(@PathVariable Integer id, Model model) {
        // RECUPERO L'OFFERTA CON QUELL'ID DA DATABASE
        Optional<Offerta> offertaRecovery = offertaRepository.findById(id);
        // SE E' PRESENTE PRECARICO IL FORM CON L'OFFERTA
        if (offertaRecovery.isPresent()) {
            // LO PASSO COME ATTRIBUTO AL MODEL
            model.addAttribute("offerta", offertaRecovery.get());
            // RESTITUISCO IL TEMPLATE
            return "offerta/editOffert";
        } else {
            // ALTRIMENTI SOLLEVO UN ECCEZIONE HTTP 404
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Offerta with id " + id
                    + " not found");
        }
    }

    // METODO PER RICEVERE IL SUBMIT DEL FORM DI EDIT
    @PostMapping("/edit/{id}")
    public String saveEditOffer(@PathVariable Integer id, Model model, @Valid @ModelAttribute("offerta") Offerta formOfferta, BindingResult bindingResult) {
        // VALIDO L'OFFERTA
        if (bindingResult.hasErrors()) {
            // SE CI SONO ERRORI RICARICO LA PAGINA COL FORM DI EDIT
            return "offerta/edit";
        }
        // SE NON CI SONO ERRORI LO SALVO SUL DATABASE
        Offerta updatedOfferta = offertaRepository.save(formOfferta);
        // REDIRECT AL DETTAGLIO DELL'OFFERTA DELLA PIZZA
        return "redirect:/pizze/show/" + formOfferta.getPizza().getName();
    }

    // METODO PER CANCELLARE L'OFFERTA PRESA PER ID
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        // VERIFICO SE L'OFFERTA CON L'ID PASSATO ESISTE
        Optional<Offerta> result = offertaRepository.findById(id);
        // SE ESISTE LA ELIMINO DAL DATABASE
        if (result.isPresent()) {
            Offerta offertaToDelete = result.get();
            offertaRepository.delete(offertaToDelete);
            return "redirect:/pizze/show/" + offertaToDelete.getPizza().getId();
        } else {
            // SE NON ESISTE SOLLEVO UN ECCEZIONE HTTP 404
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "L'offerta con id " + id + " non è stata trovata");
        }
    }
}


