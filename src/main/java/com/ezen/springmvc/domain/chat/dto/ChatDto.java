package com.ezen.springmvc.domain.chat.dto;

import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder

public class ChatDto {

    // 메시지 타입 : 입장, 채팅
    public enum MessageType {
        ENTER, TALK
    }

    private MessageType type; // 메시지 타입
    private String roomId; // 방번호
    private String senderId; // 메시지 보낸사람
    private String receiverId; // 메시지 받은사람
    private String message; // 메시지 - 삭제 예정!!!!! UUID 때문에 있는것
    private Timestamp createdAt; // 생성시간

    public static ChatDto create() {
        ChatDto chatDto = new ChatDto();
        chatDto.roomId = UUID.randomUUID().toString();

        return chatDto;
    }

}
