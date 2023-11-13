package com.krokod1lda.wishes.controllers;

import com.krokod1lda.wishes.EntityAttributes.SearchAttributes;
import com.krokod1lda.wishes.services.PersonService;
import com.krokod1lda.wishes.services.WantyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {
    @Autowired
    PersonService personService;
    @Autowired
    WantyService wantyService;

    @GetMapping("/searchByQuery")
    public String searchByQuery(@RequestParam("query") String query, Model model) {
        model.addAttribute(SearchAttributes.TITLE.getValue(), SearchAttributes.SEARCH.getValue());

        if(!query.isEmpty()) {
            model.addAttribute(SearchAttributes.MAP.getValue(), personService.getAllThePeople());
            model.addAttribute(SearchAttributes.RESULTS.getValue(), wantyService.getWanty(query));
            model.addAttribute(SearchAttributes.QUERY.getValue(), query);

            return "search";
        }

        return "redirect:/";
    }

    @GetMapping("/searchByPerson")
    public String searchByPerson(@RequestParam("buyerId") long buyerId,
                          @RequestParam("sellerId") long sellerId,
                          @RequestParam("clientId") long clientId,
                          Model model) {

        model.addAttribute(SearchAttributes.TITLE.getValue(), SearchAttributes.SEARCH.getValue());

        model.addAttribute(SearchAttributes.MAP.getValue(), personService.getAllThePeople());

        if(buyerId != 0) {
            model.addAttribute(SearchAttributes.RESULTS.getValue(), wantyService.getWishesByBuyer(buyerId));
            model.addAttribute(SearchAttributes.PERSON_ID.getValue(), buyerId);

            return "search";
        }
        else if(sellerId != 0) {
            model.addAttribute(SearchAttributes.RESULTS.getValue(), wantyService.getWishesBySeller(sellerId));
            model.addAttribute(SearchAttributes.PERSON_ID.getValue(), sellerId);

            return "search";
        }
        else if(clientId != 0) {
            model.addAttribute(SearchAttributes.RESULTS.getValue(), wantyService.getWishesByClient(clientId));
            model.addAttribute(SearchAttributes.PERSON_ID.getValue(), clientId);

            return "search";
        }

        return "redirect:/";
    }
}
