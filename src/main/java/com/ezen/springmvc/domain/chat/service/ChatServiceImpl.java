package com.ezen.springmvc.domain.chat.service;

import com.ezen.springmvc.domain.chat.dto.ChatDto;
import com.ezen.springmvc.domain.chat.mapper.ChatMapper;
import com.ezen.springmvc.domain.member.dto.MemberDto;
import com.ezen.springmvc.domain.member.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatMapper chatMapper;
    @Autowired
    private MemberMapper memberMapper;


    @Override
    public void enterRoom(ChatDto chatDto) {
        chatMapper.createChat(chatDto);
    }


    @Override
    @Transactional
    public void sendMessage(String roomId, String sender, String message) {
        chatMapper.sendMessage(roomId, sender, message);
    }

    @Override
    public List<ChatDto> getAllRooms() {
        return chatMapper.findChatAll();
    }

    @Override
    public MemberDto getMemberInfo(String id, String nickName) {
        return memberMapper.findById(id);
    }
}
