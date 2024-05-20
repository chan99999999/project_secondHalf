package com.ezen.springmvc.web.daily.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/daily")
@Slf4j
public class DailyController {

    @GetMapping
    public String dailyList(){
        return "/daily/dailyList";
    }

    @GetMapping("/register")
    public String dailyRegister(){
        return "/daily/dailyRegister";
    }

    @GetMapping("/read")
    public String dailyRead(){
        return "/daily/dailyRead";
    }
}
