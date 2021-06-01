package com.example.hw3;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
class SearchController {

    @RequestMapping("/")
    public String startPage() {
        return "search.html";
    }

    @RequestMapping("/google")
    public  String searchGoogle(@RequestParam("search_google") String search){
        String query = "redirect:https://www.google.com/search?q=" + search;
        return query;
    }

    @RequestMapping("/yandex")
    public  String searchYandex(@RequestParam("search_yandex") String search){
        String query = "redirect:https://yandex.ru/search/?lr=2&text=" + search;
        return query;
    }

    @RequestMapping("/duck")
    public  String searchDuck(@RequestParam("search_duck")  String search){
        String query = "redirect:https://duckduckgo.com/?q=" + search;
        return query;
    }
}