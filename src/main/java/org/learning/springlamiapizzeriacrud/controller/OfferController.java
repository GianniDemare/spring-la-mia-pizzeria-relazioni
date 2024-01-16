package org.learning.springlamiapizzeriacrud.controller;

import org.learning.springlamiapizzeriacrud.model.Offer;
import org.learning.springlamiapizzeriacrud.model.Pizza;
import org.learning.springlamiapizzeriacrud.repository.OfferRepository;
import org.learning.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/offers")
public class OfferController {
    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private OfferRepository offerRepository;

    @GetMapping("/create")
    public String create(@RequestParam(name = "pizzaId", required = true) Integer pizzaId,
                         Model model) {

        Optional<Pizza> result = pizzaRepository.findById(pizzaId);
        if (result.isPresent()) {

            Pizza pizzaToOffer = result.get();

            model.addAttribute("pizza", pizzaToOffer);

            Offer newOffer = new Offer();

            newOffer.setPizza(pizzaToOffer);
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
    public String store(Offer formOffer) {
        Offer storedOffer = offerRepository.save(formOffer);
        return "redirect:/pizze/show/" + storedOffer.getPizza().getId();
    }
}


