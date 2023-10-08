package com.krokod1lda.wishes.controllers;

import com.krokod1lda.wishes.models.Person;
import com.krokod1lda.wishes.models.Wanty;
import com.krokod1lda.wishes.repositories.PersonRepository;
import com.krokod1lda.wishes.repositories.WantyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

import static com.krokod1lda.wishes.controllers.PersonController.getAllThePeople;

@Controller
public class SearchController {
    @Autowired
    WantyRepository wantyRepository;
    @Autowired
    PersonRepository personRepository;
    @GetMapping("/searchByQuery")
    public String searchByQuery(@RequestParam("query") String query, Model model) {
        model.addAttribute("title", "Поиск");

        if(!query.isEmpty()) {
            model.addAttribute("map", getAllPeople());
            List<Wanty> wishes = wantyRepository.findByWantyName(query);

            model.addAttribute("results", wishes);
            model.addAttribute("query", query);
            return "search";
        }

        return "redirect:/";
    }

    @GetMapping("/searchByPerson")
    public String searchByPerson(@RequestParam("buyerId") long buyerId,
                          @RequestParam("sellerId") long sellerId,
                          @RequestParam("clientId") long clientId,
                          Model model) {

        model.addAttribute("title", "Поиск");

        model.addAttribute("map", getAllPeople());

        if(buyerId != 0) {
            List<Wanty> wishes = wantyRepository.findByBuyerId(buyerId);
            model.addAttribute("results", wishes);
            model.addAttribute("personId", buyerId);

            return "search";
        }
        else if(sellerId != 0) {
            List<Wanty> wishes = wantyRepository.findBySellerId(sellerId);
            model.addAttribute("results", wishes);
            model.addAttribute("personId", sellerId);

            return "search";
        }
        else if(clientId != 0) {
            List<Wanty> wishes = wantyRepository.findByClientId(clientId);
            model.addAttribute("results", wishes);
            model.addAttribute("personId", clientId);

            return "search";
        }

        return "redirect:/";
    }

    public Map<String, List<Person>> getAllPeople () {
        return getAllThePeople(personRepository);
    }
}
