package com.ezen.springmvc.web.member.controller;

import com.ezen.springmvc.domain.member.dto.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// REST API 서비스 전용 컨트롤러
//@RestController
@RequestMapping("/member")
@Slf4j
public class MemberRestController {

    // 회원 가입 처리
    @PostMapping("/signup")
    public MemberDto registerAction(@RequestBody MemberDto memberDto){
        log.info("수신한 사용자 정보: {}", memberDto.toString());
        return memberDto;
    }

    // 회원 로그인 처리
    @PostMapping("/signin")
    public boolean loginAction(@RequestBody Map<String, String> map){
        log.info("수신된 아이디: {}, 비번: {}", map.get("id"), map.get("passwd"));
        // 임시 이동
        return true;
    }
    
    // 회원 로그아웃 처리
    
    // 회원 목록 처리
    @GetMapping
    public List<MemberDto> list(){
        List<MemberDto> list = new ArrayList<>();
//        list.add(MemberDto.builder().id("bangry1").build());
//        list.add(MemberDto.builder().id("bangry2").build());
//        list.add(MemberDto.builder().id("bangry3").build());
        return list;
    }

    // 회원 상세 정보 처리
    // REST URL 설계
    // /member/bangry
    @GetMapping("/{memberId}")
    public MemberDto read(@PathVariable("memberId") String memberId){
        log.info("수신한 사용자 아이디 : {}", memberId);
//        MemberDto memberDto = MemberDto.builder().id("bangry").name("김기정").build();
//        return memberDto;
        return null;
    }

}









