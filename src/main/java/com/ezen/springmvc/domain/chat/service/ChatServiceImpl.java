package com.ezen.springmvc.domain.chat.service;

import com.ezen.springmvc.domain.chat.dto.ChatDto;
import com.ezen.springmvc.domain.chat.dto.MessageDto;
import com.ezen.springmvc.domain.chat.mapper.ChatMapper;
import com.ezen.springmvc.domain.chat.mapper.MessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {


    private final ChatMapper chatMapper;
    private final MessageMapper messageMapper;


    @Override
    public void newChat(ChatDto chatDto) {
        chatMapper.createChat(chatDto);
    }

    @Override
    @Transactional
    public void newMessage(String roomId, String sender, String content) {
        messageMapper.sendMessage(roomId, sender, content);
    }

    @Override
    public List<ChatDto> getByNick(String nickname) {
        return chatMapper.findChatByNick(nickname);
    }

    @Override
    public List<ChatDto> getAllRooms() {
        return chatMapper.findChatAll();
    }

    @Override
    public List<MessageDto> getMessagesByRoomId(String roomId) {
        return messageMapper.findMessagesByRoomId(roomId);
    }


}
