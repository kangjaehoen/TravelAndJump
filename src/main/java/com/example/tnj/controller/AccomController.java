package com.example.tnj.controller;

import mybatis.dao.AccomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccomController {
    @Autowired
    private AccomMapper accomMapper;

    @RequestMapping("/")
    public String categoryList(Model model){

        model.addAttribute("city" , accomMapper.categoryAccomList("도시"));
        model.addAttribute("ocean" , accomMapper.categoryAccomList("바다"));
        model.addAttribute("mountain",accomMapper.categoryAccomList("산"));
        model.addAttribute("island", accomMapper.categoryAccomList("섬"));
        model.addAttribute("countryside", accomMapper.categoryAccomList("시골"));

        return "main";

    }

    @GetMapping("/nav")
    public String accTypeList(String accType,  @RequestParam(required = false) String search, Model model){
        model.addAttribute("search",accomMapper.accTypeAccomList(accType));

        if (search != null && !search.isEmpty()) {
            model.addAttribute("search", accomMapper.searchAccomList(search));
            model.addAttribute("searchString", search);
        }
        return "search";

    }

    @GetMapping("/search")
    public String searchResultList(Model model, String search){
        model.addAttribute("search", accomMapper.searchAccomList(search));
        model.addAttribute("searchString", search);
        return "search";
    }

}
