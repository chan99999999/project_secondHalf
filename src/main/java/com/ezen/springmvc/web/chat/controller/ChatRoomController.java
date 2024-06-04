package com.ezen.springmvc.web.chat.controller;

import com.ezen.springmvc.domain.chat.dto.ChatDto;
import com.ezen.springmvc.domain.chat.dto.MessageDto;
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
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
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
    private final ChatServiceImpl chatService;
    private final SimpMessageSendingOperations messagingTemplate;
    private final MemberService memberService;


    @MessageMapping("/chat/message")
    public void message(MessageDto message) {
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    @GetMapping
    public String rooms(Model model) {
        List<ChatDto> chatList = chatService.getMyChatList(); log.info(chatList.toString());
        model.addAttribute("chatList", chatList);
        model.addAttribute("messageForm", new MessageForm());
        return "/chat/chatting";
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
    public String createRoom(@PathVariable("nickname") String nickname, HttpSession session) {

        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");

        MemberDto receiver = memberService.getNickname(nickname);

        log.info("로그인 정보는 {}", loginMember.toString());
        String roomId = UUID.randomUUID().toString();


        //같은리시버 아이디가 있는지 검색하는 매퍼
        List<ChatDto> chatList = chatService.getMyChatList();
        ChatDto findChatDto = null;
        for (ChatDto chatDto : chatList) {
            if(chatDto.getReceiverNickname().equalsIgnoreCase(nickname)){
                findChatDto = chatDto;
                break;
            }
        }

        //없으면 새로생성
        if(findChatDto == null) {
            ChatDto chatDto = ChatDto.builder()
                    .senderId(loginMember.getMemberId())
                    .senderNickname(loginMember.getNickname())
                    .receiverId(receiver.getMemberId())
                    .receiverNickname(nickname)
                    .roomId(roomId)
                    .build();

            chatService.newChat(chatDto);
        } else {
            // 기존 채팅이 존재할 경우에는, 해당 채팅방으로 리다이렉트
            return "redirect:/chat/room/enter/" + findChatDto.getRoomId();
        }
        return "redirect:/chat";
    }


    @PostMapping("/saveMessage")
    public ResponseEntity<?> receiveMessage(@RequestBody MessageDto messageDto, HttpSession session) {
        log.info("뭐받았냐 {}", messageDto.toString());
        log.info(messageDto.getRoomId());
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
