package com.krokod1lda.wishes.controllers;

import com.krokod1lda.wishes.models.Person;
import com.krokod1lda.wishes.repo.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

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

    @GetMapping("/all-persons")
    public String allPersons(Model model) {


        List<Person> sellers = personRepository.findByType("seller");
        List<Person> buyers = personRepository.findByType("buyer");
        List<Person> clients = personRepository.findByType("client");

        Map<String, List<Person>> map = new LinkedHashMap<>();

        map.put("Продавцы", sellers);
        map.put("Байеры", buyers);
        map.put("Клиенты", clients);

        if (sellers.isEmpty()) {
            map.remove("Продавцы");
        }
        if (buyers.isEmpty()) {
            map.remove("Байеры");
        }
        if (clients.isEmpty()) {
            map.remove("Клиенты");
        }

        model.addAttribute("map", map);
        model.addAttribute("title", "Все участники");
        return "all-persons";
    }

    @GetMapping("/person{id}")
    public String updatePerson(@PathVariable(value = "id") long id, Model model) {

        if (!personRepository.existsById(id))
            return "redirect:/";

        Optional<Person> person = personRepository.findById(id);
        ArrayList<Person> res = new ArrayList<>();
        person.ifPresent(res::add);

        model.addAttribute("person", res);
        model.addAttribute("title", "Редактирование участника");
        model.addAttribute("id", id);

        return "edit-person";
    }

    @PostMapping("/person{id}")
    public String updatePerson(@RequestParam("id") long id, @RequestParam("name") String name,
                               @RequestParam("patronymic") String patronymic, @RequestParam("surname") String surname) {

        Person person = personRepository.findById(id).orElseThrow();

        person.setName(name);
        person.setPatronymic(patronymic);
        person.setSurname(surname);

        personRepository.save(person);

        return "redirect:/all-persons";
    }

    @PostMapping("/deletePerson")
    public String deletePerson(@RequestParam("id") long id) {

        personRepository.deleteById(id);

        return "redirect:/all-persons";
    }
}
