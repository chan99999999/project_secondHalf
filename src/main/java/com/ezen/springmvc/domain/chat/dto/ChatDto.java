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
    private String senderNickname;
    private String receiverNickname;
    private List<MessageDto> messages;

    /**
     * 각 방의 메세지를 역순으로 정렬 후 가장 최신메세지를 채팅 목록에 출력하기 위한 메소드
     * @return 최신메세지
     */
    public MessageDto getLatestMessage() {
        if (messages != null && !messages.isEmpty()) {
            return messages.stream()
                    .sorted(Comparator.comparing(MessageDto::getSentAt).reversed())
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

}
