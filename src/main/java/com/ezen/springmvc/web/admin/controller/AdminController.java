package com.ezen.springmvc.web.admin.controller;

import com.ezen.springmvc.domain.chat.dto.MessageDto;
import com.ezen.springmvc.domain.member.dto.MemberDto;
import com.ezen.springmvc.domain.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// REST API 서비스 전용 컨트롤러
@Controller
@RequestMapping("/admin")
@Slf4j
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<?> memberList() {
        List<MemberDto> memberList = memberService.getMembers();
        return new ResponseEntity<>(memberList, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchMembers(@RequestBody String keyword) {
        log.info("keyword: {}", keyword);
        List<MemberDto> memberList = memberService.searchMembers(keyword);
        log.info("찾은회원: {}", memberList);

        return new ResponseEntity<>(memberList, HttpStatus.OK);
    }

    @PostMapping("/member/ban")
    public ResponseEntity<?> banMember(@RequestBody MemberDto memberDto) {
        log.info("가져온 회원은요: {}", memberDto);
        if(memberDto.getGrade().equals("GENERAL")) {
            memberService.banMemberGrade(memberDto.getMemberId());
        } else {
            memberService.releaseMemberGrade(memberDto.getMemberId());
        }
        log.info("정지된 회원 : {}", memberDto.toString());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/member/fire")
    public ResponseEntity<?> fireMember(@RequestBody MemberDto memberDto) {
        memberService.deleteMember(memberDto.getMemberId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}