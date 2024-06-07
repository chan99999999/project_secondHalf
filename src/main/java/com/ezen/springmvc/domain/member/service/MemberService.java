package com.ezen.springmvc.domain.member.service;

import com.ezen.springmvc.domain.member.dto.MemberDto;

import java.util.List;

/**
// * 회원 관련 비즈니스 로직 처리 및 트랜잭션 관리
 */
public interface MemberService {
	//회원가입
	public void register(MemberDto memberDto);
	
	//로그인
	public MemberDto isMember(String id, String passwd);
	
	//회원정보
	public MemberDto getMember(String id);

	//회원정보(닉네임)
	public MemberDto getNickname(String nickname);
	
	//회원목록
	public List<MemberDto> getMembers();
	
	//회원정보 수정
	public void editMember(MemberDto member);

	//회원 닉네임 수정
	public void editNickname(MemberDto member);

	//회원 비밀번호 변경
	public void editPasswd(MemberDto member);

	//회원 대표사진 변경
	public void editPicture(MemberDto member);

	//회원 아이디 찾기
	public String searchId(String name, String nickname);

	//회원 비밀번호 찾기
	public MemberDto searchPasswd(String id, String name, String email);

	//회원 비밀번호 확인
	public String checkPasswd(String id);

	//검색으로 회원목록 찾기
	public List<MemberDto> searchMembers(String keyword);

	//회원 정지시키기
	public void banMemberGrade(String id);

	//회원 정지해제
	public void releaseMemberGrade(String id);

	//회원 삭제
	public void deleteMember(String id);
}
