package com.ezen.springmvc.web.member.controller;

import com.ezen.springmvc.domain.member.dto.MemberDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/member")
public class MemberController {

    private static final Logger log = LoggerFactory.getLogger(MemberController.class);

    // 회원 가입 화면
    @GetMapping("/signup")
    public String registerForm(){
        return "/member/registerForm";
    }
    
    // 회원 가입 처리
    @PostMapping("/signup")
    public String registerAction(@ModelAttribute("memberDto") MemberDto memberDto, Model model, RedirectAttributes redirectAttributes){
        log.info("수신한 사용자 정보: {}", memberDto.toString());
        // 서비스 객체를 이용해서 가입 처리
        //model.addAttribute("memberDto", memberDto);

        redirectAttributes.addFlashAttribute("memberDto", memberDto);
        redirectAttributes.addFlashAttribute("message", "조금 쉬었다 합시다..");

        // 가입 결과를 보여주는 뷰로 리다이렉트
        return "redirect:/member/signup/result";
    }

    // 회원 가입 결과 처리
    @GetMapping("/signup/result")
    public String registerResult(){
        return "/member/registerResult";
    }
    
    // 회원 로그인 처리
    @PostMapping("/signin")
    public String loginAction(@RequestParam(value="id", required = false, defaultValue = "guest") String id,
                              @RequestParam(value="passwd") String passwd,
                              HttpServletResponse response){
        log.info("수신된 아이디: {}, 비번: {}", id, passwd);
//        session.setAttribute("LoginId", id);
        Cookie loginCookie = new Cookie("LoginId", id);
        loginCookie.setPath("/");
        response.addCookie(loginCookie);
        // 임시 이동
        return "redirect:/member";
    }
    
    // 회원 로그아웃 처리
    
    // 회원 목록 처리
    @GetMapping
//    public String list(@SessionAttribute("LoginId") String loginId){
    public String list(@CookieValue("LoginId") String loginId){
        if(loginId != null){
            return "/member/list";
        }else{
          return  "redirect:/member/signup";
        }
    }

    // 회원 상세 정보 처리
    // REST URL 설계
    // /member/bangry
    @GetMapping("/{memberId}")
    public String read(@PathVariable("memberId") String memberId, Model model){
//        MemberDto memberDto = memberService.read(memberId);
        log.info("수신한 사용자 아이디 : {}", memberId);
        model.addAttribute("memberId", memberId);
        return "/member/read";
    }

    // 회원 정보 수정 처리
    
    // 회원 정보 삭제 처리

    // 하나의 파라미터 이름에 여러개의 값을 수신할 때
    // /params?hobby=sports&hobby=sleep
//    @PostMapping("/params")
//    public String params (MultiValueMap<String, String> hobby){
//
//        log.info("{}", hobby.toString());
////        log.info("{}", hobby.get("hobby"));
//        return "/member/list";
//    }


}









