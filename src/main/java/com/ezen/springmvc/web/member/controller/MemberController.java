package com.ezen.springmvc.web.member.controller;

import com.ezen.springmvc.domain.common.encription.EzenUtil;
import com.ezen.springmvc.domain.member.dto.MemberDto;
import com.ezen.springmvc.domain.member.mapper.MemberMapper;
import com.ezen.springmvc.domain.member.service.MemberService;
import com.ezen.springmvc.domain.member.service.MemberServiceImpl;
import com.ezen.springmvc.web.member.form.LoginForm;
import com.ezen.springmvc.web.member.form.MemberForm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

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
        memberService.register(memberDto);

        redirectAttributes.addFlashAttribute("registerMember", memberDto);
        return "redirect:/member/result";
    }

    @GetMapping("/result")
    public String result() {
        return "/member/result";
    }

    // 회워 로그인 화면
    @GetMapping("/login")
    public String login(@ModelAttribute LoginForm loginForm) {
        return "/member/loginForm";
    }

    // 회원 로그인 처리
    @PostMapping("/login")
    public String loginAction(@ModelAttribute LoginForm loginForm, HttpServletResponse response, HttpServletRequest request) {
        log.info("로그인 폼 {}", loginForm);
        MemberDto loginMember = memberService.isMember(loginForm.getLoginId(), loginForm.getLoginPasswd());
        log.info("{}", loginMember);

        if (loginMember != null) {
            if (loginForm.isRememberLoginId()) {
                Cookie saveIdCookie = new Cookie("saveId", loginMember.getMemberId());
                saveIdCookie.setMaxAge(60 * 60 * 24 * 7);
                saveIdCookie.setPath("/");
                response.addCookie(saveIdCookie);
            } else {
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("saveId")) {
                            cookie.setPath("/");
                            cookie.setMaxAge(0);
                            response.addCookie(cookie);
                        }
                    }
                }
            }
        }
        HttpSession session = request.getSession();
        session.setAttribute("loginMember", loginMember);
        return "redirect:/";
    }

    // 회원 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    // 마이페이지 페이징
    @GetMapping("/mypage")
    public String mypage() {
        return "/member/mypage";
    }

    @GetMapping("/editInfo")
    public String editInfo(@ModelAttribute MemberForm memberForm, Model model) {
        return "/member/editInfo";
    }

    @GetMapping("/editPicture")
    public String editPicture(@ModelAttribute MemberForm memberForm, Model model) {
        return "/member/editPicture";
    }

    @GetMapping("/editPasswd")
    public String editPasswd(@ModelAttribute MemberForm memberForm, Model model) {
        return "/member/editPasswd";

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

}









