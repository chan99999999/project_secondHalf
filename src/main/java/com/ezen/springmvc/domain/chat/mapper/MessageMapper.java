package com.ezen.springmvc.domain.chat.mapper;

import com.ezen.springmvc.domain.chat.dto.MessageDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {
    // 메시지 전송
    public void saveMessage(MessageDto messageDto);

    // 채팅방 메세지 조회
    List<MessageDto> findMessagesByRoomId(String roomId);


}
