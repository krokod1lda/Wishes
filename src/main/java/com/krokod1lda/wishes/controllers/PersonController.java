package com.krokod1lda.wishes.controllers;

import com.krokod1lda.wishes.models.Person;
import com.krokod1lda.wishes.repo.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PersonController {
    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/add-seller")
    public String addSeller(Model model) {
        model.addAttribute("title", "Добавление продавца");
        model.addAttribute("role", "продавца");
        model.addAttribute("type", "seller");

        return "add-person";
    }

    @GetMapping("/add-buyer")
    public String addBuyer(Model model) {
        model.addAttribute("title", "Добавление байера");
        model.addAttribute("role", "байера");
        model.addAttribute("type", "buyer");

        return "add-person";
    }

    @GetMapping("/add-client")
    public String addClient(Model model) {
        model.addAttribute("title", "Добавление клиента");
        model.addAttribute("role", "клиента");
        model.addAttribute("type", "client");

        return "add-person";
    }

    @PostMapping("/addPerson")
    public String addPerson(@RequestParam("type") String type, @RequestParam("name") String name,
                            @RequestParam("patronymic") String patronymic, @RequestParam("surname") String surname) {

        Person person = new Person(type, name, surname, patronymic);
        personRepository.save(person);

        return "redirect:/";
    }
}
