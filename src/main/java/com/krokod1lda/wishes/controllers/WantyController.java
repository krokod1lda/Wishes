package com.krokod1lda.wishes.controllers;

import com.krokod1lda.wishes.models.Person;
import com.krokod1lda.wishes.models.Wanty;
import com.krokod1lda.wishes.repo.PersonRepository;
import com.krokod1lda.wishes.repo.WantyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class WantyController {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    WantyRepository wantyRepository;

    @GetMapping("/")
    public String home(Model model) {

        Iterable<Wanty> wanties = wantyRepository.findAll();

        model.addAttribute("wanties", wanties);
        model.addAttribute("title", "Главная");

        return "main";
    }

    @GetMapping("/add-wanty")
    public String addWanty(Model model) {
        model.addAttribute("title", "Добавление запроса");

        List<Person> sellers = personRepository.findByType("seller");
        List<Person> buyers = personRepository.findByType("buyer");
        List<Person> clients = personRepository.findByType("client");

        Map<String, List<Person>> people = new HashMap<>();

        people.put("sellers", sellers);
        people.put("buyers", buyers);
        people.put("clients", clients);

        model.addAttribute("map", people);

        return "add-wanty";
    }

    @PostMapping("/add-wanty")
    public String addWanty(@RequestParam("name") String name, @RequestParam("date") String date,
                           @RequestParam("size") String size, @RequestParam("seller") String sellerId,
                           @RequestParam("buyer") String buyerId, @RequestParam("client") String clientId,
                           @RequestParam("isPurchased") boolean isPurchased, @RequestParam("description") String description) {

        Wanty wanty = new Wanty(name, date, size, Integer.parseInt(sellerId), Integer.parseInt(buyerId),
                Integer.parseInt(clientId), isPurchased, description, null);

        wantyRepository.save(wanty);

        return "redirect:/";
    }

    @GetMapping("/wanty{wantyId}")
    public String wantyCard(@PathVariable(value = "wantyId") long wantyId, Model model) {
        if (!wantyRepository.existsById(wantyId))
            return "redirect:/";

        model.addAttribute("title", "Карточка запроса");

        Optional<Wanty> wanty = wantyRepository.findById(wantyId);
        ArrayList<Wanty> res = new ArrayList<>();
        wanty.ifPresent(res::add);
        model.addAttribute("wanty", res);

        Person seller = personRepository.findById(res.get(0).getSellerId()).orElseThrow();
        model.addAttribute("sellerName", seller.getSurname() +
                " " + seller.getName() + " " + seller.getPatronymic());

        Person buyer = personRepository.findById(res.get(0).getBuyerId()).orElseThrow();
        model.addAttribute("buyerName", buyer.getSurname() +
                " " + buyer.getName() + " " + buyer.getPatronymic());

        Person client = personRepository.findById(res.get(0).getClientId()).orElseThrow();
        model.addAttribute("clientName", client.getSurname() +
                " " + client.getName() + " " + client.getPatronymic());

        model.addAttribute("isPurchased", wanty.get().isPurchased() ? "был выкуплен" : "не был выкуплен");

        return "wanty-card";
    }
}
