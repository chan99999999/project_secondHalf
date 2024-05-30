package com.ezen.springmvc.web.chat.controller;

import com.ezen.springmvc.domain.chat.dto.ChatDto;
import com.ezen.springmvc.domain.chat.repo.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    @Autowired
    private final ChatRoomRepository chatRoomRepository;

    @GetMapping
    public String rooms(Model model) {
        return "/chat/chatting";
    }

    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatDto> room() {
        return chatRoomRepository.findAllRoom();
    }

    @PostMapping("/room")
    @ResponseBody
    public ChatDto createRoom() {
        return chatRoomRepository.createChatRoom();
    }

    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "/chat/chatting";
    }

    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatDto roomInfo(@PathVariable String roomId) {
        return chatRoomRepository.findRoomById(roomId);
    }
}
