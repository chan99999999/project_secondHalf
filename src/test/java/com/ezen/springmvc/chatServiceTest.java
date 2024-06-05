package com.ezen.springmvc;

import com.ezen.springmvc.domain.chat.dto.ChatDto;
import com.ezen.springmvc.domain.chat.dto.MessageDto;
import com.ezen.springmvc.domain.chat.service.ChatService;
import com.ezen.springmvc.domain.member.dto.MemberDto;
import com.ezen.springmvc.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class chatServiceTest {

	@Autowired
	private ChatService chatService;

//	@Test
//	@DisplayName("메세지 목록")
//	@Disabled
//	void printMessage(){
//		List<MessageDto> list = chatService.getMessagesByRoomId("9874c6ab-d578-4d7d-8ac1-955ff4de2a43");
//		for (MessageDto messageDto : list) {
//			log.info(messageDto.toString());
//		}
//	}

	@Test
	@DisplayName("메세지 저장")
//	@Disabled
	void saveMessageTest(){
		MessageDto messageDto = MessageDto.builder()
				.roomId("1")
				.senderId("찬찬")
				.content("왜그러냐고")
						.build();
		chatService.receiveMessage(messageDto);
	}
}







