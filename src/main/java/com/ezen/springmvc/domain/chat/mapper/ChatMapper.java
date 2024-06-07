package com.ezen.springmvc.domain.chat.mapper;

import com.ezen.springmvc.domain.chat.dto.ChatDto;
import com.ezen.springmvc.domain.chat.dto.MessageDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChatMapper {

    /**
     * 채팅방 생성
     * @param chatDto
     */
    public void createChat(ChatDto chatDto);


    /**
     * 채팅방 목록 조회
     * @return 계정별 채팅목록
     */
    public List<ChatDto> findMyChatList();


    /**
     * 닉네임으로 채팅방 검색
     * @param nickname
     * @return 채팅방
     */
    List<ChatDto> findChatByNick(@Param("nickname") String nickname);

    /**
     * 룸아이디로 채팅방 조회
     * @param roomId
     * @return 채팅방
     */
    ChatDto findChatRoom(String roomId);

    /**
     * 같은 receiverId가 있는지 조회
     * @return 아이디
     */
    ChatDto findSameReceiver();

    ChatDto findChatRoomBySenderAndReceiver(String senderId, String receiverId);

}
