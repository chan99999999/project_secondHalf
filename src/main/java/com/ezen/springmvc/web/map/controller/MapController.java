package com.ezen.springmvc.web.map.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/apimap")
    public String apiMap(){
        return "/map/apiMap";
    }
}
