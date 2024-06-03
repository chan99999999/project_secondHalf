package com.ezen.springmvc.domain.chat.dto;

import lombok.*;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder

public class ChatDto {


    private String roomId; // 방번호
    private String senderId; // 메시지 보낸사람
    private String receiverId; // 메시지 받은사람
    private Timestamp createdAt; // 생성시간
    private List<MessageDto> messages;


    public MessageDto getLatestMessage() {
        if (messages != null && !messages.isEmpty()) {
            // 리스트를 시간순으로 정렬한 후 첫 번째 메시지 반환
            return messages.stream()
                    .sorted(Comparator.comparing(MessageDto::getSentAt).reversed())
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

}
