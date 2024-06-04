package com.ezen.springmvc.web.chat.controller;

import com.ezen.springmvc.domain.chat.dto.ChatDto;
import com.ezen.springmvc.domain.chat.dto.MessageDto;
import com.ezen.springmvc.domain.chat.repo.ChatRoomRepository;
import com.ezen.springmvc.domain.chat.service.ChatService;
import com.ezen.springmvc.domain.chat.service.ChatServiceImpl;
import com.ezen.springmvc.domain.member.dto.MemberDto;
import com.ezen.springmvc.domain.member.service.MemberService;
import com.ezen.springmvc.web.chat.form.MessageForm;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import oracle.net.ns.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    private static final Logger log = LoggerFactory.getLogger(ChatRoomController.class);
    private final ChatRoomRepository chatRoomRepository;
    private final ChatServiceImpl chatService;

    @GetMapping
    public String rooms(Model model) {
        List<ChatDto> chatList = chatService.getMyChatList();
        model.addAttribute("chatList", chatList);
        model.addAttribute("messageForm", new MessageForm());
        return "/chat/chatting";
    }


    @PostMapping("/room")
    @ResponseBody
    public ChatDto createRoom() {
        return chatRoomRepository.createChatRoom();
    }

    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(RedirectAttributes redirectAttributes, @PathVariable String roomId) {
        log.info(" 대화방 입장 : {}", roomId);
        List<MessageDto> messages = chatService.getMessagesByRoomId(roomId);
        for (MessageDto message : messages) {
            log.info("메세지 {}", message.getContent());
        }
        redirectAttributes.addFlashAttribute("messages", messages);
        redirectAttributes.addFlashAttribute("roomId", roomId);
        return "redirect:/chat";
    }

    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatDto roomInfo(@PathVariable String roomId) {
        return chatService.getRoom(roomId);
    }


    @GetMapping("/room/to/{nickname}")
    public String createRoomEx(@PathVariable("nickname") String nickname, HttpSession session) {

        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");

        log.info("로그인 정보는 {}", loginMember.toString());
        String roomId = UUID.randomUUID().toString();

        //챗 디티오 전체리스트 출력하기
        //거기서 리시버 아이디가 같은 것이 있는지 검색하는 매퍼
        //같으면 기존 findChatDto를 출력

//        if(findChatDto == null) {
            ChatDto chatDto = ChatDto.builder()
                    .senderId(loginMember.getMemberId())
                    .receiverId(nickname)
                    .roomId(roomId)
                    .build();

            chatService.newChat(chatDto);
//        } else {
//        }

        return "redirect:/chat";
    }


    @PostMapping("/saveMessage")
    public ResponseEntity<?> receiveMessage(@RequestBody MessageDto messageDto, HttpSession session) {
        log.info("뭐받았냐 {}", messageDto.toString());


        MessageDto saveMessage = MessageDto.builder()
                .roomId(messageDto.getRoomId())
                .content(messageDto.getContent())
                .sentAt(messageDto.getSentAt())
                .senderId(messageDto.getSenderId())
                .build();
        chatService.receiveMessage(saveMessage);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
