package com.krokod1lda.wishes.controllers;

import com.krokod1lda.wishes.models.Person;
import com.krokod1lda.wishes.models.Wanty;
import com.krokod1lda.wishes.repositories.PersonRepository;
import com.krokod1lda.wishes.services.PersonService;
import com.krokod1lda.wishes.services.WantyService;
import org.springframework.beans.factory.annotation.Autowired;
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
    PersonService personService;
    @Autowired
    WantyService wantyService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Главная");
        model.addAttribute("wanties", wantyService.getAllTheWanties());
        model.addAttribute("map", personService.getAllThePeople());

        return "main";
    }

    @GetMapping("/add-wanty")
    public String addWanty(Model model) {
        model.addAttribute("title", "Добавление запроса");

        model.addAttribute("map", personService.getAllThePeople());

        return "add-wanty";
    }

    @PostMapping("/add-wanty")
    public String addWanty(@RequestParam("name") String name, @RequestParam("date") String date,
                           @RequestParam("size") String size, @RequestParam("seller") long sellerId,
                           @RequestParam("buyer") long buyerId, @RequestParam("client") long clientId,
                           @RequestParam("isPurchased") boolean isPurchased, @RequestParam("description") String description) {

        wantyService.addWanty(name, date, size, sellerId, buyerId, clientId, isPurchased, description);

        return "redirect:/";
    }

    // NEED TO REFACTOR THIS METHOD
    @GetMapping("/wanty{wantyId}")
    public String wantyCard(@PathVariable(value = "wantyId") long wantyId, Model model) {
        model.addAttribute("title", "Карточка запроса");

        ArrayList<Wanty> wanty = wantyService.getWanty(wantyId);
        model.addAttribute("wanty", wanty);

        model.addAttribute("sellerName", personService.getPersonFullName(wanty.get(0).getSellerId()));

        model.addAttribute("buyerName", personService.getPersonFullName(wanty.get(0).getBuyerId()));

        model.addAttribute("clientName", personService.getPersonFullName(wanty.get(0).getClientId()));

        model.addAttribute("isPurchased", wanty.get(0).isPurchased() ? "был выкуплен" : "не был выкуплен");

        return "wanty-card";
    }

    // NEED TO REFACTOR THIS METHOD TOO
    @GetMapping("/wanty{wantyId}/edit")
    public String editWanty(@PathVariable("wantyId") long wantyId, Model model) {
        model.addAttribute("title", "Редактирование запроса");

        List<Wanty> wanty = wantyService.getWanty(wantyId);
        model.addAttribute("wanty", wanty);

        List<Person> curSeller = personService.getPersonAsList(wanty.get(0).getSellerId());
        List<Person> curBuyer = personService.getPersonAsList(wanty.get(0).getBuyerId());
        List<Person> curClient = personService.getPersonAsList(wanty.get(0).getClientId());

        model.addAttribute("curSeller", curSeller);
        model.addAttribute("curBuyer", curBuyer);
        model.addAttribute("curClient", curClient);

        model.addAttribute("map",
                personService.removeCurrents(curSeller.get(0), curBuyer.get(0), curClient.get(0)));

        return "edit-wanty";
    }

    @PostMapping("/wanty{wantyId}/edit")
    public String editWanty(@PathVariable("wantyId") long wantyId, @RequestParam("name") String name,
                            @RequestParam("date") String date, @RequestParam("size") String size,
                            @RequestParam("seller") long sellerId, @RequestParam("buyer") long buyerId,
                            @RequestParam("client") long clientId, @RequestParam("isPurchased") boolean isPurchased,
                            @RequestParam("description") String description) {

        wantyService.updateWanty(wantyId, name, date, size, sellerId, buyerId,
                clientId, isPurchased, description);

        return "redirect:/";
    }

    @PostMapping("/wanty{wantyId}/delete")
    public String deleteWanty(@PathVariable("wantyId") long wantyId) {
        wantyService.deleteWanty(wantyId);

        return "redirect:/";
    }
}