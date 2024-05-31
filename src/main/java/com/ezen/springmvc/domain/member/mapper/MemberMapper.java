package com.ezen.springmvc.domain.member.mapper;

import com.ezen.springmvc.domain.member.dto.MemberDto;
import com.ezen.springmvc.domain.member.dto.MemberSearchCondition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MemberMapper {
    //회원 생성
    public void create(MemberDto member);

    //로그인
    public MemberDto findByIdAndPasswd(@Param("memberId") String id, @Param("memberPasswd") String passwd);

    //회원정보 수정
    public void updateInfo(MemberDto member);

    //비밀번호 변경
    public void updatePasswd(MemberDto member);

    //대표사진 변경
    public void updatePicture(MemberDto member);

    //아이디로 회원찾기
    public MemberDto findById(String id);

    //회원목록 조회
    public List<MemberDto> findByAll();

}

