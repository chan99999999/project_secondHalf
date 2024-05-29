package com.ezen.springmvc.domain.chat.mapper;

import com.ezen.springmvc.domain.chat.dto.ChatDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMapper {
    //채팅방 생성
    public void createChat(ChatDto chatDto);

    //채팅방 삭제
    public void deleteChat(ChatDto chatDto);

    //채팅방 전체목록 조회
    public List<ChatDto> findChatAll();

    //닉네임으로 검색... sender_id  에서 닉네임으로 어떻게 뽑지 join?
    public List<ChatDto> findChatByNick(String sender);

    // 메시지 전송
    public void sendMessage(String roomId, String sender, String message);

    //채팅내용 검색(이름/닉네임으로 하려면 MemberDto 인가?)


}
