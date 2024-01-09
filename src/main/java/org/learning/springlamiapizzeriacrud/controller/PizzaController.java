package org.learning.springlamiapizzeriacrud.controller;

import org.learning.springlamiapizzeriacrud.model.Pizza;
import org.learning.springlamiapizzeriacrud.repository.PizzeriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/pizze")
public class PizzaController {

    @Autowired
    private PizzeriaRepository pizzeriaRepository;

    @GetMapping
    public String index(Model model) {
        List<Pizza> pizze = pizzeriaRepository.findAll();
        model.addAttribute("pizze", pizze);
        return "pizza/index";
    }


}
