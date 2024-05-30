package com.ezen.springmvc.web.chat.controller;

import com.ezen.springmvc.domain.chat.dto.ChatDto;
import com.ezen.springmvc.domain.chat.dto.MessageDto;
import com.ezen.springmvc.domain.chat.repo.ChatRoomRepository;
import com.ezen.springmvc.domain.chat.service.ChatService;
import com.ezen.springmvc.domain.chat.service.ChatServiceImpl;
import lombok.RequiredArgsConstructor;
import oracle.net.ns.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    @Autowired
    private final ChatRoomRepository chatRoomRepository;

    @Autowired
    private final ChatServiceImpl chatService;

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

    @PostMapping("/message")
    public ResponseEntity<String> sendMessage(@RequestBody MessageDto messageDto){
        // sentAt에 현재시간 설정
        messageDto.setSentAt(Timestamp.from(Instant.now()));
        chatService.newMessage(messageDto);
        return new ResponseEntity<>("Message sent successfully", HttpStatus.OK);
    }
}
