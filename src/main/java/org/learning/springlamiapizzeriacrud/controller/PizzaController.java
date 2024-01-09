package org.learning.springlamiapizzeriacrud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home/PizzaList")
public class PizzaController {
    @GetMapping
    public String pizzaList() {
        return "pizza/pizzaList";
    }
}
