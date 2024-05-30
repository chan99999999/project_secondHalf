package com.ezen.springmvc;

import com.ezen.springmvc.domain.member.dto.MemberDto;
import com.ezen.springmvc.domain.member.dto.MemberSearchCondition;
import com.ezen.springmvc.domain.member.mapper.MemberMapper;
import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
class MemberMapperTest {

    @Autowired
    MemberMapper memberMapper;

    @Test
    @DisplayName("회원 전체 조회 테스트")
    @Disabled
    void findAllTest() {
        List<MemberDto> list  = memberMapper.findByAll();
        for (MemberDto memberDto : list) {
            log.info("회원: {}", memberDto);
        }
    }

    @Test
    @DisplayName("회원 상세 조회 테스트")
    @Disabled
    public void findByIdTest() {
        // given
        String memberId = "bangry";
        // when
        MemberDto member = memberMapper.findById(memberId);
        // then
        assertThat(member)
                .isNotNull();
        log.info("회원정보 : {}", member.toString());
    }

    @Test
    @DisplayName("회원 인증 테스트")
    @Disabled
    public void findByIdAndPasswdTest() {
        // given
        String id = "bangry";
        String passwd = "1111";
        // when
        MemberDto memberDto = memberMapper.findByIdAndPasswd(id, passwd);
        // then
        assertThat(memberDto)
                .isNotNull();
        log.info("로드인 회원정보 : {}", memberDto.toString());
    }

    @Test
    @DisplayName("회원 등록 테스트")
    @Disabled
    void createTest() {
        MemberDto createMember = MemberDto
                .builder()
                .memberId("chan999")
                .name("김찬규")
                .nickname("찬찬")
                .memberPasswd("1111")
                .memberAddress("wolgye")
                .gender("M")
                .birthDate("20/05/14")
                .email("chan999@gmail.com")
                .build();
        memberMapper.create(createMember);
        log.info("회원 등록 완료 : {}", createMember);
    }

    @Test
    @DisplayName("회원 정보 수정 테스트")
    @Disabled
    void updateTest() {
        MemberDto updateMember = MemberDto
                .builder()
                .memberId("chat")
                .nickname("채팅")
                .email("chan999@aa")
                .hobby("누워")
                .interest("음슴")
                .introduce("안냐세여 저는 21살 귀염둥이임다")
                .build();
        memberMapper.updateInfo(updateMember);
        log.info("회원 수정 완료 : {}", updateMember);
    }


    @Test
    @DisplayName("회원 비밀번호 변경 테스트")
    @Disabled
    void updatePasswdTest() {
        MemberDto memberDto = MemberDto.builder()
                .memberId("chan999")
                .memberPasswd("2222")
                .build();

        memberMapper.updatePasswd(memberDto);
        log.info("회원 비밀번호 변경완료{}", memberDto);
    }

    @Test
    @DisplayName("회원 대표사진 변경 테스트")
    @Disabled
    void updatePictureTest(){
        MemberDto memberDto = MemberDto.builder()
                .memberId("chan999")
                .picture("xxx.jpg")
                .storePicture("xxx-124.jpg")
                .build();
        memberMapper.updatePicture(memberDto);
    }

    @Test
    @DisplayName("회원 아이디 찾기")
    @Disabled
    void findMemberIdTest(){
        String memberId = memberMapper.findMemberId("먼데이", "월요일");
        log.info("회원정보 : {}", memberId);
    }

    @Test
    @DisplayName("회원 비밀번호 찾기")
    @Disabled
    void findMemberPasswdTest(){
        MemberDto memberDto = memberMapper.findMemberPasswd("chan999", "김찬규", "chan999@gmail.com");
        log.info("회워정보 : {}", memberDto);
    }

    @Test
    @DisplayName("닉네임으로 회원찾기")
    @Disabled
    void findMemberByNicknameTest(){
    }

    @Test
    @DisplayName("닉네임 변경")
    @Disabled
    void editNicknameTest(){
        MemberDto memberDto = MemberDto.builder()
                .nickname("김김")
                .memberId("chan999")
                .build();
        memberMapper.updateNickname(memberDto);
    }

    @Test
    @DisplayName("비밀번호 확인 테스트")
    @Disabled
    void checkPasswdTest(){
        String passwd = memberMapper.confirmPasswd("chan999");
        log.info("현재 비밀번호 : {}", passwd);
    }
}








