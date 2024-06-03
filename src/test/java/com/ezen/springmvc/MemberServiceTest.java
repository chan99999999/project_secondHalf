package com.ezen.springmvc;

import com.ezen.springmvc.domain.member.dto.MemberDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.ezen.springmvc.domain.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@SpringBootTest
@Slf4j
public class MemberServiceTest {
	
	@Autowired
	private MemberService memberService;

	@Test
	@DisplayName("회원 전체 조회 테스트")
	@Disabled
	void getMembersTest(){
		List<MemberDto> list =  memberService.getMembers();
		log.info("회원 전체 목록 : {}", list);
	}

	@Test
	@DisplayName("회원 가입 테스트")
	@Disabled
	void registerMemberTest(){
	}
	
	@Test
	@DisplayName("회원 인증 테스트")
	@Disabled
	void isMemberTest() {
		String id = "chan999", passwd = "1111";
		MemberDto isMember = memberService.isMember(id, passwd);
		log.info("인증 사용자 정보 : {}", isMember);
	}


	@Test
	@DisplayName("회원정보 수정 테스트")
	@Disabled
	void memberEditTest(){
		String id = "chat";
		MemberDto memberDto = MemberDto.builder()
				.memberId(id)
				.nickname("키키")
				.email("ewq@adsa")
				.hobby("놉")
				.interest("눕")
				.introduce("납")
				.build();

		memberService.editMember(memberDto);
	}

	@Test
	@DisplayName("닉네임으로 회원찾기")
	@Disabled
	void findByNick(){
		MemberDto memberDto = memberService.getNickname("찬찬");
		log.info(memberDto.toString());
	}

	@Test
	@DisplayName("회원 전체조회")
	@Disabled
	void findAll(){
		List<MemberDto> memberList = memberService.getMembers();
		for (MemberDto memberDto : memberList) {
			log.info(memberDto.toString());
		}
	}

	@Test
	@DisplayName("회원검색")
	@Disabled
	void searchMemberTest(){
		List<MemberDto> list = memberService.searchMembers("김");
		for (MemberDto memberDto : list) {
			log.info("찾은회원 : {}", memberDto.toString());
		}
	}

	@Test
	@DisplayName("회원정지")
	@Disabled
	void banMemberTest(){
		memberService.banMemberGrade("chan777");
		MemberDto memberDto = memberService.getMember("chan777");
		log.info("변경된 회원 : {}", memberDto.toString());
	}
}







