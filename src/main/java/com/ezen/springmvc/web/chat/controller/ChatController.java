package com.ezen.springmvc.web.chat.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
@Slf4j
public class ChatController {

    @GetMapping
    public String chatting(){
        return "/chat/chatting";
    }
}
