package com.krokod1lda.wishes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WantyController {

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("title", "Главная");
        return "main";
    }

    @GetMapping("/add-wanty")
    public String addWanty(Model model) {

        model.addAttribute("title", "Добавление запроса");
        return "add-wanty";
    }
}
