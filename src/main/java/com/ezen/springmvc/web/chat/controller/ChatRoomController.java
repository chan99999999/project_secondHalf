package com.ezen.springmvc.web.chat.controller;

import com.ezen.springmvc.domain.chat.dto.ChatDto;
import com.ezen.springmvc.domain.chat.dto.MessageDto;
import com.ezen.springmvc.domain.chat.repo.ChatRoomRepository;
import com.ezen.springmvc.domain.chat.service.ChatService;
import com.ezen.springmvc.domain.chat.service.ChatServiceImpl;
import com.ezen.springmvc.domain.member.dto.MemberDto;
import com.ezen.springmvc.web.chat.form.MessageForm;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import oracle.net.ns.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

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
        List<ChatDto> chatList = chatService.getMyChatList();
        model.addAttribute("chatList", chatList);
        model.addAttribute("messageForm", new MessageForm());
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

    @PostMapping("/sendMessage/{roomId}")
    public ResponseEntity<Resource> sendMessage(@PathVariable("roomId") String roomId, @ModelAttribute("messageForm") MessageForm messageForm){
        // sentAt에 현재시간 설정

        ChatDto chatDto = chatService.getRoom(roomId);

        MessageDto messageDto = MessageDto.builder()
                .sentAt(Timestamp.from(Instant.now()))
                .content(messageForm.getInputMessage())
                .roomId(roomId)
                .senderId(chatDto.getSenderId())
                .build();

        chatService.newMessage(messageDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/room/{nickname}")
    public String createRoomEx(@PathVariable("nickname") String nickname, HttpSession session){

        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
        String roomId = UUID.randomUUID().toString();

        ChatDto chatDto = ChatDto.builder()
                .senderId(loginMember.getNickname())
                .receiverId(nickname)
                .roomId(roomId)
                .build();

        chatService.newChat(chatDto);
        return "/chat";
    }
}
