package com.ezen.springmvc.web.member.controller;

import com.ezen.springmvc.domain.common.encription.EzenUtil;
import com.ezen.springmvc.domain.member.dto.MemberDto;
import com.ezen.springmvc.domain.member.mapper.MemberMapper;
import com.ezen.springmvc.domain.member.service.MemberService;
import com.ezen.springmvc.domain.member.service.MemberServiceImpl;
import com.ezen.springmvc.web.member.form.MemberForm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// REST API 서비스 전용 컨트롤러
@Controller
@RequestMapping("/member")
@Slf4j
public class MemberController {

    private final MemberMapper memberMapper;
    private final MemberServiceImpl memberServiceImpl;

    public MemberController(MemberMapper memberMapper, MemberServiceImpl memberServiceImpl) {
        this.memberMapper = memberMapper;
        this.memberServiceImpl = memberServiceImpl;
    }

    // 회원가입 화면 이동
    @GetMapping("/signup")
    public String signup() {
        return "/member/signUpForm";
    }

    // 회원 가입 처리
    @PostMapping("/signup")
    public String registerAction(@ModelAttribute MemberForm memberForm, Model model, RedirectAttributes redirectAttributes) {
        log.info("수신한 사용자 정보: {}", memberForm.toString());

        MemberDto memberDto = MemberDto.builder()
                .memberId(memberForm.getMemberId())
                .name(memberForm.getName())
                .nickname(memberForm.getNickname())
                .memberPasswd(memberForm.getMemberPasswd())
                .memberAddress(memberForm.getMemberAddress())
                .gender(memberForm.getGender())
                .birthDate(memberForm.getBirthDate())
                .email(memberForm.getEmail())
                .build();
        memberServiceImpl.register(memberDto);

        redirectAttributes.addFlashAttribute("registerMember", memberDto);
        return "redirect:/member/result";
    }

    @GetMapping("/result")
    public String result() {
        return "/member/result";
    }

    // 회워 로그인 화면
    @GetMapping("/login")
    public String login() {
        return "/member/loginForm";
    }

    // 회원 로그인 처리
    @PostMapping("/login")
    public String loginAction(@RequestParam("loginId") String id, @RequestParam("loginPw") String pw, @RequestParam("saveId") String saveId, HttpServletResponse response, HttpSession session) {
        MemberService memberService = new MemberServiceImpl(memberMapper);
        MemberDto loginMember = memberService.isMember(id, pw);
        log.info("아이디 : {}, 비밀번호 :  {}", id, pw);

        if (loginMember != null) {
            if (saveId != null) {
                Cookie saveIdCookie = new Cookie("cookieId", EzenUtil.encription(id));
                saveIdCookie.setMaxAge(60 * 60 * 24 * 7);
                saveIdCookie.setPath("/");
                response.addCookie(saveIdCookie);
            } else {
                Cookie saveIdCookie = new Cookie("cookieId", EzenUtil.encription(id));
                saveIdCookie.setMaxAge(0);
                saveIdCookie.setPath("/");
                response.addCookie(saveIdCookie);
            }
            session.setAttribute("loginMember", loginMember);
        }
        return "redirect:/";
    }

    //

    // 회원 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loginMember");
        return "redirect:/";
    }

    // 회원 상세 정보 처리
    // REST URL 설계


    // /member/bangry
//    @GetMapping("/{memberId}")
//    public MemberDto read(@PathVariable("memberId") String memberId){
//        log.info("수신한 사용자 아이디 : {}", memberId);
////        MemberDto memberDto = MemberDto.builder().id("bangry").name("김기정").build();
////        return memberDto;
//        return null;
//    }
}









