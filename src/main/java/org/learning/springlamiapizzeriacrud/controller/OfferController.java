package org.learning.springlamiapizzeriacrud.controller;

import jakarta.validation.Valid;
import org.learning.springlamiapizzeriacrud.model.Offer;
import org.learning.springlamiapizzeriacrud.model.Pizza;
import org.learning.springlamiapizzeriacrud.repository.OfferRepository;
import org.learning.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/discounts")
public class OfferController {
    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private OfferRepository discountRepository;

    @GetMapping("/create")
    public String offer(@RequestParam(name = "pizzaId", required = true) Integer pizzaId,
                         Model model) {

        Optional<Pizza> result = pizzaRepository.findById(pizzaId);
        if (result.isPresent()) {

            Pizza pizzaOnSale = result.get();

            model.addAttribute("pizza", pizzaOnSale);

            Offer newOffer = new Offer();

            newOffer.setPizza(pizzaOnSale);
            newOffer.setStartDate(LocalDate.now());
            newOffer.setExpireDate(LocalDate.now().plusDays(30));
            model.addAttribute("offer", newOffer);

            return "offers/create";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Pizza with id " + pizzaId + " not found");
        }
    }

    @PostMapping("/create")
    public String menu(@Valid @ModelAttribute("offer") Offer formOffer, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "offers/create";
        }

        Offer menuOffer = discountRepository.save(formOffer);

        return " redirect:/pizze/show/" + menuOffer.getPizza().getId();

    }
}


