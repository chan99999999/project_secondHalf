package com.ezen.springmvc.domain.chat.mapper;

import com.ezen.springmvc.domain.chat.dto.ChatDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChatMapper {
    //채팅방 생성
    public void createChat(ChatDto chatDto);

    //채팅방 전체목록 조회
    public List<ChatDto> findChatAll();

    //닉네임으로 채팅방 검색
    List<ChatDto> findChatByNick(@Param("nickname") String nickname);

    //룸아이디로 채팅방 조회
    ChatDto findChatRoom(String roomId);
}
