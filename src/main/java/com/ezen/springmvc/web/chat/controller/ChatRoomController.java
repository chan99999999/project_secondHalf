package com.ezen.springmvc.web.chat.controller;

import com.ezen.springmvc.domain.chat.dto.ChatDto;
import com.ezen.springmvc.domain.chat.dto.MessageDto;
import com.ezen.springmvc.domain.chat.service.ChatServiceImpl;
import com.ezen.springmvc.domain.member.dto.MemberDto;
import com.ezen.springmvc.domain.member.service.MemberService;
import com.ezen.springmvc.web.chat.form.MessageForm;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.UUID;

/**
 * 채팅 애플리케이션의 채팅방을 관리하는 컨트롤러
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    private static final Logger log = LoggerFactory.getLogger(ChatRoomController.class);
    private final ChatServiceImpl chatService;
    private final SimpMessageSendingOperations messagingTemplate;
    private final MemberService memberService;


    /**
     * 클라이언트로부터 받은 메시지를 해당 채팅방의 구독자(sub)에게 전달
     * @param message 클라이언트로부터 받은 메시지 객체
     */
    @MessageMapping("/chat/message")
    public void message(MessageDto message) {
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }


    /**
     * 대화방 목록을 가져와서 대화방 목록 페이지를 반환
     * @param model Thymeleaf 모델 객체
     * @return 대화방 목록 페이지의 뷰 이름
     */
    @GetMapping
    public String rooms(Model model, HttpSession session) {

        MemberDto memberDto = (MemberDto) session.getAttribute("loginMember");

        List<ChatDto> chatList = chatService.getByNick(memberDto.getNickname());
        model.addAttribute("chatList", chatList);
        model.addAttribute("messageForm", new MessageForm());
        return "/chat/chatting";
    }


    /**
     * 특정 대화방에 입장하는 요청을 처리하고, 해당 대화방의 메시지를 가져와 리다이렉트
     * @param redirectAttributes 리다이렉트 시 데이터 전달을 위한 RedirectAttributes 객체
     * @param roomId
     * @return 대화방 페이지로의 리다이렉트
     */
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(RedirectAttributes redirectAttributes, @PathVariable String roomId) {
//        log.info(" 대화방 입장 : {}", roomId);
        List<MessageDto> messages = chatService.getMessagesByRoomId(roomId);
//        for (MessageDto message : messages) {
//            log.info("메세지 {}", message.getContent());
//        }
        ChatDto chatDto = chatService.getRoom(roomId);
        MemberDto memberDto = memberService.getMember(chatDto.getReceiverId());

        redirectAttributes.addFlashAttribute("messages", messages);
        redirectAttributes.addFlashAttribute("roomId", roomId);
        redirectAttributes.addFlashAttribute("receiver", memberDto);
        return "redirect:/chat";
    }


    /**
     * 특정 대화방 정보를 가져오는 요청 처리
     * @param roomId
     * @return 대화방 정보를 포함한 ChatDto 객체
     */
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatDto roomInfo(@PathVariable String roomId) {
        return chatService.getRoom(roomId);
    }


    /**
     * 새로운 대화방을 생성하고, 해당 대화방으로 리다이렉트
     * @param nickname 대화 상대방의 닉네임
     * @param session HTTP 세션 객체
     * @return 대화방 페이지로의 리다이렉트
     */
//    @GetMapping("/room/to/{nickname}")
//    public String createRoom(@PathVariable("nickname") String nickname, HttpSession session) {
//        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
//        MemberDto receiver = memberService.getNickname(nickname);
////        log.info("로그인 정보는 {}", loginMember.toString());
//        String roomId = UUID.randomUUID().toString();
//
//        //같은리시버 아이디가 있는지 검색
//        List<ChatDto> chatList = chatService.getMyChatList();
//        ChatDto findChatDto = null;
//        for (ChatDto chatDto : chatList) {
//            if(chatDto.getReceiverNickname().equalsIgnoreCase(nickname)){
//                findChatDto = chatDto;
//                break;
//            }
//        }
//
//        //없으면 새로생성, 있으면 해당 대화방으로 리다이렉트
//        if(findChatDto == null) {
//            ChatDto chatDto = ChatDto.builder()
//                    .senderId(loginMember.getMemberId())
//                    .senderNickname(loginMember.getNickname())
//                    .receiverId(receiver.getMemberId())
//                    .receiverNickname(nickname)
//                    .roomId(roomId)
//                    .build();
//            chatService.newChat(chatDto);
//        } else {
//            return "redirect:/chat/room/enter/" + findChatDto.getRoomId();
//        }
//        return "redirect:/chat";
//    }

    @GetMapping("/room/to/{nickname}")
    public String createRoom(@PathVariable("nickname") String nickname, HttpSession session) {
        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
        MemberDto receiver = memberService.getNickname(nickname);
        String roomId = UUID.randomUUID().toString();

        // 같은 수신자가 있는지 검색
        ChatDto existingChat = chatService.findChatRoomBySenderAndReceiver(loginMember.getMemberId(), receiver.getMemberId());

        // 없으면 새로 생성, 있으면 해당 대화방으로 리다이렉트
        if (existingChat == null) {
            ChatDto chatDto = ChatDto.builder()
                    .senderId(loginMember.getMemberId())
                    .senderNickname(loginMember.getNickname())
                    .receiverId(receiver.getMemberId())
                    .receiverNickname(nickname)
                    .roomId(roomId)
                    .build();
            chatService.newChat(chatDto);
            return "redirect:/chat";
        } else {
            return "redirect:/chat/room/enter/" + existingChat.getRoomId();
        }
    }


    /**
     * 메시지를 저장하는 요청 처리
     * @param messageDto 메시지 정보를 포함한 MessageDto 객체
     * @param session HTTP 세션 객체
     * @return ResponseEntity 객체로 응답반환
     */
//    @PostMapping("/saveMessage")
//    public ResponseEntity<?> receiveMessage(@RequestBody MessageDto messageDto, HttpSession session) {
////        log.info("뭐받았냐 {}", messageDto.toString());
////        log.info(messageDto.getRoomId());
//        MessageDto saveMessage = MessageDto.builder()
//                .roomId(messageDto.getRoomId())
//                .content(messageDto.getContent())
//                .sentAt(messageDto.getSentAt())
//                .senderId(messageDto.getSenderId())
//                .senderProfileImage(((MemberDto) session.getAttribute("loginMember")).getStorePicture())  // 현재 로그인한 사용자의 프로필 이미지 설정
//                .build();
//        chatService.receiveMessage(saveMessage);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @PostMapping("/saveMessage")
    public ResponseEntity<?> receiveMessage(@RequestBody MessageDto messageDto, HttpSession session) {
        // 세션에서 로그인 멤버 정보를 가져옵니다.
        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");

        // 로그인을 통해 세션에 저장된 멤버가 있는지 확인합니다.
        if (loginMember == null) {
            log.error("로그인 정보가 없습니다.");
            return new ResponseEntity<>("로그인 정보가 없습니다.", HttpStatus.UNAUTHORIZED);
        }

        log.info("로그인 멤버: {}", loginMember.toString());

        // 프로필 이미지가 제대로 설정되었는지 확인합니다.
        String senderProfileImage = loginMember.getStorePicture();
        if (senderProfileImage == null || senderProfileImage.isEmpty()) {
            log.error("프로필 이미지가 설정되지 않았습니다.");
            return new ResponseEntity<>("프로필 이미지가 설정되지 않았습니다.", HttpStatus.BAD_REQUEST);
        }

        log.info("프로필 이미지: {}", senderProfileImage);

        // 메시지 객체를 빌드하여 저장합니다.
        MessageDto saveMessage = MessageDto.builder()
                .roomId(messageDto.getRoomId())
                .content(messageDto.getContent())
                .sentAt(messageDto.getSentAt())
                .senderId(messageDto.getSenderId())
                .senderProfileImage(messageDto.getSenderProfileImage())  // 현재 로그인한 사용자의 프로필 이미지 설정
                .build();

        // 메시지를 저장합니다.
        chatService.receiveMessage(saveMessage);

        // 응답을 반환합니다.
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
