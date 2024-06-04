package com.ezen.springmvc.domain.chat.mapper;

import com.ezen.springmvc.domain.chat.dto.MessageDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {

    /**
     * 전송된 메세지를 db에 저장
     * @param messageDto
     */
    public void saveMessage(MessageDto messageDto);

    /**
     * 각 채팅방 메세지 화면에 조회
     * @param roomId
     * @return 채팅메세지
     */
    List<MessageDto> findMessagesByRoomId(String roomId);

}
