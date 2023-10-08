package com.krokod1lda.wishes.controllers;

import com.krokod1lda.wishes.models.Person;
import com.krokod1lda.wishes.models.Wanty;
import com.krokod1lda.wishes.repositories.PersonRepository;
import com.krokod1lda.wishes.repositories.WantyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

import static com.krokod1lda.wishes.controllers.PersonController.getAllThePeople;

@Controller
public class WantyController {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    WantyRepository wantyRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Главная");

        Iterable<Wanty> wanties = wantyRepository.findAll();

        model.addAttribute("wanties", wanties);
        model.addAttribute("map", getAllPeople());

        return "main";
    }

    @GetMapping("/add-wanty")
    public String addWanty(Model model) {
        model.addAttribute("title", "Добавление запроса");

        model.addAttribute("map", getAllPeople());

        return "add-wanty";
    }

    @PostMapping("/add-wanty")
    public String addWanty(@RequestParam("name") String name, @RequestParam("date") String date,
                           @RequestParam("size") String size, @RequestParam("seller") long sellerId,
                           @RequestParam("buyer") long buyerId, @RequestParam("client") long clientId,
                           @RequestParam("isPurchased") boolean isPurchased, @RequestParam("description") String description) {

        Wanty wanty = new Wanty(name, date, size, sellerId, buyerId, clientId,
                isPurchased, description, null);

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

    @GetMapping("/wanty{wantyId}/edit")
    public String editWanty(@PathVariable("wantyId") long wantyId, Model model) {
        if (!wantyRepository.existsById(wantyId))
            return "redirect:/";

        model.addAttribute("title", "Редактирование запроса");

        Optional<Wanty> wanty = wantyRepository.findById(wantyId);
        List<Wanty> res = new ArrayList<>();
        wanty.ifPresent(res::add);
        model.addAttribute("wanty", res);

        Optional<Person> curSeller = personRepository.findById(res.get(0).getSellerId());
        List<Person> currentSeller = new ArrayList<>();
        curSeller.ifPresent(currentSeller::add);
        model.addAttribute("curSeller", currentSeller);

        Optional<Person> curBuyer = personRepository.findById(res.get(0).getBuyerId());
        List<Person> currentBuyer = new ArrayList<>();
        curBuyer.ifPresent(currentBuyer::add);
        model.addAttribute("curBuyer", currentBuyer);

        Optional<Person> curClient = personRepository.findById(res.get(0).getClientId());
        List<Person> currentClient = new ArrayList<>();
        curClient.ifPresent(currentClient::add);
        model.addAttribute("curClient", currentClient);

        List<Person> sellers = personRepository.findByType("seller");
        sellers.remove(curSeller.get());

        List<Person> buyers = personRepository.findByType("buyer");
        buyers.remove(curBuyer.get());

        List<Person> clients = personRepository.findByType("client");
        clients.remove(curClient.get());

        Map<String, List<Person>> people = new HashMap<>();

        people.put("sellers", sellers);
        people.put("buyers", buyers);
        people.put("clients", clients);

        model.addAttribute("map", people);

        return "edit-wanty";
    }

    @PostMapping("/wanty{wantyId}/edit")
    public String editWanty(@PathVariable("wantyId") long wantyId, @RequestParam("name") String name,
                            @RequestParam("date") String date, @RequestParam("size") String size,
                            @RequestParam("seller") long sellerId, @RequestParam("buyer") long buyerId,
                            @RequestParam("client") long clientId, @RequestParam("isPurchased") boolean isPurchased,
                            @RequestParam("description") String description) {

        Wanty wanty = wantyRepository.findById(wantyId).orElseThrow();

        wanty.update(name, date, size, sellerId, buyerId,
                clientId, isPurchased, description, null);

        wantyRepository.save(wanty);

        return "redirect:/";
    }

    @PostMapping("/wanty{wantyId}/delete")
    public String deleteWanty(@PathVariable("wantyId") long wantyId) {
        Wanty wanty = wantyRepository.findById(wantyId).orElseThrow();
        wantyRepository.delete(wanty);

        return "redirect:/";
    }

    public Map<String, List<Person>> getAllPeople () {
        return getAllThePeople(personRepository);
    }
}
