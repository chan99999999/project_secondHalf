package com.ezen.springmvc.domain.chat.service;

import com.ezen.springmvc.domain.chat.dto.ChatDto;
import com.ezen.springmvc.domain.member.dto.MemberDto;

import java.util.List;

/**
 * 채팅 관련 비즈니스 로직 처리 및 트랜잭션 관리
 */
public interface ChatService {
    //채팅방 입장
    public void enterRoom(ChatDto chatDto);

    //메시지 보내기
    public void sendMessage(String roomId, String sender, String message);

    //채팅방 조회
    public List<ChatDto> getAllRooms();

    // 회원정보 출력
    public MemberDto getMemberInfo(String id, String nickName);


}
