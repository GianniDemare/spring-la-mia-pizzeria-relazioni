package org.learning.springlamiapizzeriacrud.controller;

import jakarta.validation.Valid;
import org.learning.springlamiapizzeriacrud.model.Ingredienti;
import org.learning.springlamiapizzeriacrud.model.Offerta;
import org.learning.springlamiapizzeriacrud.repository.IngredientiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Controller
@RequestMapping("/ingredients")
public class IngredientiController {
    @Autowired
    private IngredientiRepository ingredientiRepository;

    // METODO INDEX CHE MOSTRA LA LISTA DI TUTTI GLI INGREDIENTI
    @GetMapping
    public String index(Model model) {
        // AL TEMPLATE DEVO FARE ARRIVARE LA LISTA DI TUTTI GLI INGREDIENTI
        model.addAttribute("ingredientiList", ingredientiRepository.findAll());
        return "ingredienti/index";
    }

    // METODO CREATE CHE MOSTRA LA PAGINA COL FORM DI CREAZIONE DEGLI INGREDIENTI
    @GetMapping("/create")
    public String create(Model model) {
        // PREPARO IL TEMPLATE COL FORM DI CREAZIONE INGREDIENTI
        model.addAttribute("formIngredienti", new Ingredienti());
        return "ingredienti/form";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("formIngredienti") Ingredienti formIngredienti, BindingResult bindingResult) {
        // VALIDO GLI INGREDIENTI
        if (bindingResult.hasErrors()) {
            // SE CI SONO ERRORI RICARICO LA PAGINA COL FORM
            return "ingredienti/form";
        }
        // SE NON CI SONO ERRORI SALVO LA CATEGORY SUL DATABASE
        ingredientiRepository.save(formIngredienti);
        // FACCIO LA REDIRECT ALLA LISTA DI INGREDIENTI
        return "redirect:/ingredienti";
    }

    // METODO CHE RESTITUISCE LA PAGINA DI MODIFICA DEGLI INGREDIENTI
    @GetMapping("edit/{id}")
    public String edit(@PathVariable Integer id) {
        Optional<Ingredienti> result = ingredientiRepository.findById(id);
        if (result.isPresent()) {
            return "ingredienti/form";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Gli ingredienti con id " + id + " non sono stati trovati");
        }
    }

    // METODO CHE RICEVE IL SUBMIT DEL FORM DI EDIT
    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute("ingredienti") Ingredienti formIngredienti, BindingResult bindingResult) {
        Optional<Ingredienti> result = ingredientiRepository.findById(id);
        if (result.isPresent()) {
            Ingredienti ingredientiToEdit = result.get();
            return "ingredienti/edit";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Gli ingredienti con id " + id + " non sono stati trovati");
        }
    }

    // METODO PER CANCELLARE GLI INGREDIENTI PER ID
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        // VERIFICO SE GLI INGREDIENTIL'OFFERTA CON L'ID PASSATO ESISTE
        Optional<Ingredienti> result = ingredientiRepository.findById(id);
        // SE ESISTE LA ELIMINO DAL DATABASE
        if (result.isPresent()) {
            ingredientiRepository.deleteById(id);
            return "redirect:/ingredienti";
        } else {
            // SE NON ESISTE SOLLEVO UN ECCEZIONE HTTP 404
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "L'offerta con id " + id + " non è stata trovata");
        }
    }
}
