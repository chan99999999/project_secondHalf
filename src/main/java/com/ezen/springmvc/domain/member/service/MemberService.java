package com.ezen.springmvc.domain.member.service;

import com.ezen.springmvc.domain.member.dto.MemberDto;

import java.util.List;

/**
 * 회원 관련 비즈니스 로직 처리 및 트랜잭션 관리
 */
public interface MemberService {
	//회원가입
	public void register(MemberDto memberDto);
	
	//로그인
	public MemberDto isMember(String id, String passwd);
	
	//회원정보
	public MemberDto getMember(String id);
	
	//회원목록
	public List<MemberDto> getMembers();
	
	//회원정보 수정
	public void editMember(MemberDto member);
}
