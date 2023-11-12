package com.krokod1lda.wishes.controllers;

import com.krokod1lda.wishes.SoldInfo;
import com.krokod1lda.wishes.models.Person;
import com.krokod1lda.wishes.models.Wanty;
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
import java.sql.Date;

enum WantyAttributes {
    TITLE("title"),
    MAP("map"),
    WANTY("wanty"),
    STATISTICS("Статистика"),
    WANTIES("wanties");

    private final String value;

    WantyAttributes(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

@Controller
public class WantyController {
    @Autowired
    PersonService personService;
    @Autowired
    WantyService wantyService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute(WantyAttributes.TITLE.getValue(), "Главная");
        model.addAttribute("wanties", wantyService.getAllTheWanties());
        model.addAttribute(WantyAttributes.MAP.getValue(), personService.getAllThePeople());

        return "main";
    }

    @GetMapping("/add-wanty")
    public String addWanty(Model model) {
        model.addAttribute(WantyAttributes.TITLE.getValue(), "Добавление запроса");

        model.addAttribute(WantyAttributes.MAP.getValue(), personService.getAllThePeople());

        return "add-wanty";
    }

    @PostMapping("/add-wanty")
    public String addWanty(@RequestParam("name") String name, @RequestParam("date") Date date,
                           @RequestParam("size") String size, @RequestParam("seller") long sellerId,
                           @RequestParam("buyer") long buyerId, @RequestParam("client") long clientId,
                           @RequestParam("isPurchased") boolean isPurchased, @RequestParam("description") String description) {

        wantyService.addWanty(name, date, size, sellerId, buyerId, clientId, isPurchased, description);

        return "redirect:/";
    }

    @GetMapping("/wanty/{wantyId}")
    public String wantyCard(@PathVariable(value = "wantyId") long wantyId, Model model) {
        model.addAttribute(WantyAttributes.TITLE.getValue(), "Карточка запроса");

        ArrayList<Wanty> wanty = wantyService.getWanty(wantyId);
        model.addAttribute(WantyAttributes.WANTY.getValue(), wanty);

        model.addAttribute("sellerName", personService.getPersonFullName(wanty.get(0).getSellerId()));

        model.addAttribute("buyerName", personService.getPersonFullName(wanty.get(0).getBuyerId()));

        model.addAttribute("clientName", personService.getPersonFullName(wanty.get(0).getClientId()));

        model.addAttribute("isPurchased", wanty.get(0).isPurchased() ? "был куплен" : "не был куплен");

        return "wanty-card";
    }

    @GetMapping("/wanty/{wantyId}/edit")
    public String editWanty(@PathVariable("wantyId") long wantyId, Model model) {
        model.addAttribute(WantyAttributes.TITLE.getValue(), "Редактирование запроса");

        List<Wanty> wanty = wantyService.getWanty(wantyId);
        model.addAttribute(WantyAttributes.WANTY.getValue(), wanty);

        List<Person> curSeller = personService.getPersonAsList(wanty.get(0).getSellerId());
        List<Person> curBuyer = personService.getPersonAsList(wanty.get(0).getBuyerId());
        List<Person> curClient = personService.getPersonAsList(wanty.get(0).getClientId());

        model.addAttribute("curSeller", curSeller);
        model.addAttribute("curBuyer", curBuyer);
        model.addAttribute("curClient", curClient);

        model.addAttribute(WantyAttributes.MAP.getValue(),
                personService.removeCurrents(curSeller.get(0), curBuyer.get(0), curClient.get(0)));

        return "edit-wanty";
    }

    @PostMapping("/wanty/{wantyId}/edit")
    public String editWanty(@PathVariable("wantyId") long wantyId, @RequestParam("name") String name,
                            @RequestParam("date") Date date, @RequestParam("size") String size,
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

    @GetMapping("/start-statistics")
    public String startStatistics(Model model) {
        model.addAttribute(PersonAttributes.TITLE.getValue(), WantyAttributes.STATISTICS.getValue());

        ArrayList<HashMap<String, SoldInfo>> wanties = wantyService.getStartStatistics();

        model.addAttribute("wantiesSeller", wanties.get(0));
        model.addAttribute("wantiesBuyer", wanties.get(1));
        model.addAttribute("wantiesClient", wanties.get(2));

        model.addAttribute("date1", null);
        model.addAttribute("date2", null);

        return "statistics";
    }

    @GetMapping("/statistics")
    public String statistics(@RequestParam("date1") Date date1, @RequestParam("date2") Date date2, Model model) {
        model.addAttribute(PersonAttributes.TITLE.getValue(), WantyAttributes.STATISTICS.getValue());

        ArrayList<HashMap<String, SoldInfo>> wanties = wantyService.getStatistics(date1, date2);

        model.addAttribute("wantiesSeller", wanties.get(0));
        model.addAttribute("wantiesBuyer", wanties.get(1));
        model.addAttribute("wantiesClient", wanties.get(2));

        model.addAttribute("date1", date1);
        model.addAttribute("date2", date2);

        return "statistics";
    }
}