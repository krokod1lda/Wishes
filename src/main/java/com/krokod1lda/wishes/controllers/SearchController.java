package com.krokod1lda.wishes.controllers;

import com.krokod1lda.wishes.models.Wanty;
import com.krokod1lda.wishes.repositories.WantyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {
    @Autowired
    WantyRepository wantyRepository;
    @GetMapping("/search")
    public String searchInWishes(@RequestParam("query") String query, Model model) {
        if(!query.isEmpty()) {
            List<Wanty> wishes = wantyRepository.findByWantyName(query);

            model.addAttribute("results", wishes);
            model.addAttribute("query", query);
            return "search";
        }

        return "redirect:/";
    }
}
