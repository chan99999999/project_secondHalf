package com.ezen.springmvc.domain.chat.repo;

import com.ezen.springmvc.domain.chat.dto.ChatDto;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class ChatRoomRepository {

    private Map<String, ChatDto> chatRoomMap;

    @PostConstruct
    private void init() {
        chatRoomMap = new LinkedHashMap<>();
    }

    public List<ChatDto> findAllRoom() {
        // 채팅방 생성순서 최근 순으로 반환
        List chatRooms = new ArrayList<>(chatRoomMap.values());
        Collections.reverse(chatRooms);
        return chatRooms;
    }

    public ChatDto findRoomById(String id) {
        return chatRoomMap.get(id);
    }

    public ChatDto createChatRoom() {
        ChatDto chatRoom = ChatDto.create();
        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }
}
