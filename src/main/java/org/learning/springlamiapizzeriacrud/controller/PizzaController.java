package org.learning.springlamiapizzeriacrud.controller;

import jakarta.validation.Valid;
import org.learning.springlamiapizzeriacrud.model.Pizza;
import org.learning.springlamiapizzeriacrud.repository.IngredientiRepository;
import org.learning.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizze")

public class PizzaController {
    // IL CONTROLLER HA BISOGNO DELLE FUNZIONALITA DEL REPOSITORY

    // REPOSITORY PIZZA
    @Autowired
    private PizzaRepository pizzaRepository;

    // REPOSITORY INGREDIENTI
    @Autowired
    private IngredientiRepository ingredientiRepository;

    // METODO INDEX CHE MOSTRA LA LISTA DI TUTTE LE PIZZE
    @GetMapping
    public String index(Model model) {
        List<Pizza> pizzaList = pizzaRepository.findAll();
        // AGGIUNGO LA LISTA DI LIBRI AGLI ATTRIBUTI DEL MODEL
        model.addAttribute("pizzaList", pizzaList);
        return "pizze/list";
    }

    // METODO SHOW CHE MOSTRA I DETTAGLI DI UNA SINGOLA PIZZA
    @GetMapping("/show/{id}")
    public String show(@PathVariable Integer id, Model model) {
        // NEL CORPO DEL METODO HO L'ID DELLA PIZZA DA CERCARE
        Optional<Pizza> result = pizzaRepository.findById(id);
        // VERIFICO SE LA PIZZA E' STATA TROVATA
        if (result.isPresent()) {
            // ESTRAGGO LA PIZZA DALL'OPTIONAL
            Pizza pizza = result.get();
            // AGGIUNGO AL MODEL L'ATTRIBUTO CON LA PIZZA
            model.addAttribute("pizza", pizza);
            return "pizze/show";
        } else {
            // GESTISCO IL CASO IN CUI NEL DATABASE UN BOOK CON QUELL'ID NON C'E
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza with id " + id + " not found");
        }
    }

    // METODO CREATE CHE MOSTRA LA PAGINA COL FORM DI CREAZIONE DI UNA PIZZA
    @GetMapping("/create")
    public String create(Model model) {
        Pizza pizza = new Pizza();
        // book.setTitle("Default title");
        // PASSO TRAMITE MODEL UN ATTRIBUTO DI TIPO PIZZA VUOTO
        model.addAttribute("pizza", pizza);
        // PASSO TRAMITE MODEL LA LISTA DEGLI INGREDIENTI
        model.addAttribute("ingredientiList", ingredientiRepository.findAll());
        return "pizze/create";
    }

    @PostMapping("/create")
    public String personalizzata(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {
        // VALIDO I DATI DELLA PIZZA, CIOE' VERIFICO SE LA MAPPA BINDINGRESULT HA ERRORI
        if (bindingResult.hasErrors()) {
            // QUI GESTISCO CHE HO CAMPI NON VALIDI
            // RITORNO IL TEMPLATE DEL FORM
            // PASSO TRAMITE MODEL LA LISTA DI TUTTI GLI INGREDIENTI DISPONIBILI
            model.addAttribute("ingredientiList", ingredientiRepository.findAll());
            return "pizze/create";
        }
        Pizza savedPizza = pizzaRepository.save(formPizza);
        // FACCIO UN REDIRECT ALLA PAGINA DI DETTAGLIO DELLA PIZZA APPENA CREATO
        return "redirect:/pizze/show/" + savedPizza.getId();
    }

    // METODO CHE RESTITUISCE LA PAGINA DI MODIFICA DELLA PIZZA
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        // RECUPERO LA PIZZA DAL DATABASE
        Optional<Pizza> result = pizzaRepository.findById(id);
        // VERIFICO SE LA PIZZA E' PRESENTE
        if(result.isPresent()){
            // LO PASSO COME ATTRIBUTO DEL MODEL
            model.addAttribute("pizza", result.get());
            // RITORNO IL TEMPLATE
            return "pizze/edit";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza with id: " + id + " is not found");
        }
    }

    // METODO CHE RICEVE IL SUBMIT DEL FORM DI EDIT
    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id,@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult){
        {
            Optional<Pizza> result = pizzaRepository.findById(id);
            if (result.isPresent()){
                Pizza pizzaToEdit = result.get();
                // VALIDO I DATI DELLA PIZZA
                if (bindingResult.hasErrors()){
                    // SE CI SONO ERRORI DI VALIDAZIONE
                    return "/pizze/edit";
                }
                // SE SONO VALIDI SALVO LA PIZZA SUL DATABASE
                // PRIMA DI SALVARE I DATI SUL DATABASE RECUPERO IL VALORE DEL CAMPO SETPHOTO
                formPizza.setPhoto(pizzaToEdit.getPhoto());
                Pizza savedPizza = pizzaRepository.save(formPizza);
                // FACCIO LA REDIRECT ALLA PAGINA DI DETTAGLIO DELLA PIZZA
                return "redirect:/pizze/show/" + id;
            }else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza with id" + id + "not found");
            }
        }
    }

    // METODO CHE CANCELLA UNA PIZZA PRESA PER ID
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        // VERIFICO SE LA PIZZA E' PRESENTE SUL DATABASE
        Optional<Pizza> result = pizzaRepository.findById(id);
        redirectAttributes.addFlashAttribute("redirectMessage", "Pizza " + result.get().getName() + " deleted");
        if (result.isPresent()) {
            // SE C'E LO CANCELLO
            pizzaRepository.deleteById(id);
            return "redirect:/pizze";
        } else {
            // SE NON C'E SOLLEVO UN ECCEZIONE
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza with id " + id + " not found");
        }
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "search") String searchKeyword, Model model) {
        List<Pizza> pizzaList = pizzaRepository.findByNameContaining(searchKeyword);
        model.addAttribute("pizzaList", pizzaList);
        return "pizze/list";
    }


}

