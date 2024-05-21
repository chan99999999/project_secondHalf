package com.ezen.springmvc.domain.member.mapper;

import com.ezen.springmvc.domain.member.dto.MemberDto;
import com.ezen.springmvc.domain.member.dto.MemberSearchCondition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//@Repository
@Mapper
public interface MemberMapper {
    //회원 생성
    public void create(MemberDto member);

    //회원 수정
    public void update(MemberDto member);

    //아이디로 회원찾기
    public MemberDto findById(String id);

    //회원목록 조회
    public List<MemberDto> findByAll();

    //로그인
    public MemberDto findByIdAndPasswd(@Param("memberId") String id, @Param("password") String passwd);

    //이름으로 회원찾기
    public List<MemberDto> findByNameLike(String name);

    // 검색 타입별 회원 검색
    public List<MemberDto> findBySearchType(@Param("type") String type, @Param("value") String value);

    // 통합 검색
    public List<MemberDto> findBySearchAll(String value);

    // 통합 검색
    public List<MemberDto> findBySearchAllOption(MemberSearchCondition searchCondition);
}

