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

    /**
     * 채팅방 생성
     *
     * @param chatDto
     */
    @Override
    public void newChat(ChatDto chatDto) {
        chatMapper.createChat(chatDto);
    }


    /**
     * 메세지 수신
     *
     * @param messageDto
     */
    @Override
    @Transactional
    public void receiveMessage(MessageDto messageDto) {
        messageMapper.saveMessage(messageDto);
    }


    /**
     * 닉네임으로 채팅방 검색
     *
     * @param nickname
     * @return 닉네임이포함된 채팅방
     */
    @Override
    public List<ChatDto> getByNick(String nickname) {
        return chatMapper.findChatByNick(nickname);
    }


    /**
     * 계정별 채팅방리스트 조회
     *
     * @return 채팅리스트
     */
    @Override
    public List<ChatDto> getMyChatList() {
        List<ChatDto> chatList = chatMapper.findMyChatList();
        for (ChatDto chatDto : chatList) {
            List<MessageDto> messages = messageMapper.findMessagesByRoomId(chatDto.getRoomId());
            chatDto.setMessages(messages);
        }
        return chatList;
    }


    /**
     * roomId로 채팅방 구분
     *
     * @param roomId
     * @return 채팅방
     */
    @Override
    @Transactional
    public ChatDto getRoom(String roomId) {
        return chatMapper.findChatRoom(roomId);
    }


    /**
     * 채팅방 메세지 조회
     *
     * @param roomId
     * @return 채팅방 메세지
     */
    @Override
    public List<MessageDto> getMessagesByRoomId(String roomId) {
        return messageMapper.findMessagesByRoomId(roomId);
    }


    /**
     * 동일한 receiverId 가 있는지 조회
     *
     * @return receiverId
     */
    @Override
    public ChatDto getSameReceiver() {
        return chatMapper.findSameReceiver();
    }


    // 새로운 메서드 추가: 특정 송신자와 수신자 쌍의 기존 채팅방 찾기

    @Override
    public ChatDto findChatRoomBySenderAndReceiver(String senderId, String receiverId) {
        return chatMapper.findChatRoomBySenderAndReceiver(senderId, receiverId);
    }



}
