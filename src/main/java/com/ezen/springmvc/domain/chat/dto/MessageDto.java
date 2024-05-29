package com.ezen.springmvc.domain.chat.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class MessageDto {
    private String messageId;
    private String content;
    private Timestamp sentAt;
    private String roomId;
    private String senderId;
}
