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
    private String messageId;
    private String content;
    private Timestamp sentAt;
    private String roomId;
    private String senderId;
}
