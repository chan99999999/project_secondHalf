package com.ezen.springmvc.domain.chat.dto;

import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MessageDto {

    /**
     *  메시지 타입 : 입장, 채팅
     */
    public enum MessageType {
        ENTER, TALK
    }

    private MessageType type;
    private String messageId;
    private String content;
    private Timestamp sentAt;
    private String roomId;
    private String senderId;

    
}
