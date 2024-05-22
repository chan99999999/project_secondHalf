package com.ezen.springmvc.web.map.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/map")
@Slf4j
public class MapController {

    @GetMapping
    public String searchMap(){
        return "/map/searchForm";
    }

    @GetMapping("/place")
    public String placeMap(){
        return "/map/place";
    }

//    @GetMapping("/place")
//    public String placeMap(@RequestParam("id") String placeId, Model model) {
//        model.addAttribute("placeId", placeId);
//        return "/map/place";
//    }
}
