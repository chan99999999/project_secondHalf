package com.ezen.springmvc.domain.chat.service;

import com.ezen.springmvc.domain.chat.dto.ChatDto;
import com.ezen.springmvc.domain.chat.dto.MessageDto;

import java.util.List;

/**
 * 채팅 관련 비즈니스 로직 처리 및 트랜잭션 관리
 */
public interface ChatService {
    //채팅방 생성
    public void newChat(ChatDto chatDto);

    //메시지 전송
    public void newMessage(String roomId, String sender, String content);

    // 닉네임으로 검색
    public List<ChatDto> getByNick(String nickname);

    //채팅방 전체목록 조회
    public List<ChatDto> getAllRooms();

    // 채팅방 메세지 조회
    public List<MessageDto> getMessagesByRoomId(String roomId);




}
