package com.ezen.springmvc.domain.chat.service;

import com.ezen.springmvc.domain.chat.dto.ChatDto;
import com.ezen.springmvc.domain.chat.dto.MessageDto;

import java.util.List;

/**
 * 채팅 관련 비즈니스 로직 처리 및 트랜잭션 관리
 */
public interface ChatService {

    /**
     * 채팅방 생성
     * @param chatDto
     */
    public void newChat(ChatDto chatDto);

    /**
     * 메세지 수신
     * @param messageDto
     */
    public void receiveMessage(MessageDto messageDto);

    /**
     * 닉네임으로 채팅방 검색
     * @param nickname
     * @return 닉네임이포함된 채팅방
     */
    public List<ChatDto> getByNick(String nickname);

    /**
     * 계정별 채팅방리스트 조회
     * @return 채팅리스트
     */
    public List<ChatDto> getMyChatList();

    /**
     * roomId로 채팅방 구분
     * @param roomId
     * @return 채팅방
     */
    public ChatDto getRoom(String roomId);

    /**
     * 채팅방 메세지 조회
     * @param roomId
     * @return 채팅방 메세지
     */
    public List<MessageDto> getMessagesByRoomId(String roomId);

    /**
     * 동일한 receiverId 가 있는지 조회
     * @return receiverId
     */
    public ChatDto getSameReceiver();



}
