package com.krokod1lda.wishes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HumanController {
    @GetMapping("/add-seller")
    public String addSeller(Model model) {

        model.addAttribute("title", "Добавление продавца");
        model.addAttribute("role", "продавца");
        return "add-someone";
    }

    @GetMapping("/add-buyer")
    public String addBuyer(Model model) {

        model.addAttribute("title", "Добавление байера");
        model.addAttribute("role", "байера");
        return "add-someone";
    }

    @GetMapping("/add-client")
    public String addClient(Model model) {

        model.addAttribute("title", "Добавление клиента");
        model.addAttribute("role", "клиента");
        return "add-someone";
    }
}
