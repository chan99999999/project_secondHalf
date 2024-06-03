package com.ezen.springmvc.web.member.controller;

import com.ezen.springmvc.domain.common.dto.UploadFile;
import com.ezen.springmvc.domain.common.service.FileService;
import com.ezen.springmvc.domain.member.dto.MemberDto;
import com.ezen.springmvc.domain.member.service.MemberService;
import com.ezen.springmvc.web.member.form.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// REST API 서비스 전용 컨트롤러
@Controller
@RequestMapping("/member")
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    @Value("${upload.profile.path}")
    private String profileFileUploadPath;
    private final FileService fileService;
    private final MemberService memberService;

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;


    // 회원가입 화면 이동
    @GetMapping("/signup")
    public String signup(Model model) {
        MemberForm memberForm = MemberForm.builder().build();
        model.addAttribute("memberForm", memberForm);
        return "/member/signUpForm";
    }

    // 회원 가입 처리
    @PostMapping("/signup")
    public String registerAction(@Validated @ModelAttribute MemberForm memberForm, BindingResult bindingResult,
                                 Model model, RedirectAttributes redirectAttributes) {
        log.info("수신한 사용자 정보: {}", memberForm.toString());

        if (bindingResult.hasErrors()) {
            return "/member/signUpForm";
        }

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

    @GetMapping("/idcheck/{inputId}")
    public @ResponseBody Map<String, Object> idDupCheckAction(@PathVariable("inputId") String inputId) {
        log.info("요청 아이디 : {}", inputId);

        Map<String, Object> map = new HashMap<>();
        map.put("result", true);
        map.put("message", "사용 가능한 아이디입니다.");

        MemberDto memberDto = memberService.getMember(inputId);
        if (memberDto != null) {
            map.put("result", false);
            map.put("message", "이미 사용중인 아이디입니다.");
        }
        return map;
    }

    @GetMapping("/nicknameCheck/{inputNickname}")
    public @ResponseBody Map<String, Object> nickNameDupCheckAction(@PathVariable("inputNickname") String inputNickname) {

        Map<String, Object> map = new HashMap<>();
        if(inputNickname == null){
            map.put("result", true);
            map.put("message", "");
        }

        map.put("result", true);
        map.put("message", "사용 가능한 닉네임입니다.");

        MemberDto memberDto = memberService.getNickname(inputNickname);
        if (memberDto != null) {
            map.put("result", false);
            map.put("message", "이미 사용중인 닉네임입니다.");
        }
        return map;
    }

    @GetMapping("/passwdCheck/{inputPasswd}")
    public @ResponseBody Map<String, Object> passwdDupCheckAction(@PathVariable("inputPasswd") String inputPasswd, HttpSession session) {

        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
        Map<String, Object> map = new HashMap<>();
        map.put("result", true);
        map.put("message", "");

        String oldPasswd = memberService.checkPasswd(loginMember.getMemberId());
        if (!inputPasswd.equals(oldPasswd)) {
            map.put("result", false);
            map.put("message", "비밀번호를 올바르게 입력해주세요.");
        }
        return map;
    }

    @GetMapping("/result")
    public String result() {
        return "/member/result";
    }

    // 회워 로그인 화면
    @GetMapping("/login")
    public String login(@ModelAttribute LoginForm loginForm, @CookieValue(value = "saveId", required = false) String saveId, Model model) {
        if (saveId != null) {
            loginForm.setLoginId(saveId);
        }
        return "/member/loginForm";
    }

    // 회원 로그인 처리
    @PostMapping("/login")
    public String loginAction(@RequestParam(value = "redirectURI", required = false, defaultValue = "/") String redirectURI,
                              @ModelAttribute LoginForm loginForm, HttpServletResponse response, HttpServletRequest request) {

        MemberDto loginMember = memberService.isMember(loginForm.getLoginId(), loginForm.getLoginPasswd());

        if (loginMember == null) {
            return "/member/loginForm";
        }

        if (loginMember.getGrade().equals("BAN")) {
            String notice = "정지된 회원입니다. 고객센터에 문의바랍니다.";
            request.setAttribute("notice", notice);
            return "/member/loginForm";
        }


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
        HttpSession session = request.getSession();
        session.setAttribute("loginMember", loginMember);
        return "redirect:" + redirectURI;
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

    @PostMapping("editInfo")
    public String editInfoAction(@ModelAttribute EditForm editForm, HttpSession session) {

        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");

        MemberDto memberDto = MemberDto.builder()
                .memberId(loginMember.getMemberId())
                .email(editForm.getEmail())
                .hobby(editForm.getHobby())
                .interest(editForm.getInterest())
                .introduce(editForm.getIntroduce())
                .build();

        log.info(memberDto.toString());
        memberService.editMember(memberDto);

        MemberDto editMember = memberService.getMember(loginMember.getMemberId());

        session.setAttribute("loginMember", editMember);
        return "redirect:/member/mypage";
    }

    @GetMapping("/editPicture")
    public String editPicture(@ModelAttribute EditPictureForm editPictureForm, Model model) {
        return "/member/editPicture";
    }

    @GetMapping("/editNickname")
    public String editNickname(@ModelAttribute EditNicknameForm editNicknameForm){
        return "/member/editNickname";
    }

    @PostMapping("/editNickname")
    public String editNicknameAction(@ModelAttribute EditNicknameForm editNicknameForm, HttpSession session){

        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");

        MemberDto memberDto = MemberDto.builder()
                .nickname(editNicknameForm.getNewNickname())
                .memberId(loginMember.getMemberId())
                .build();

        memberService.editNickname(memberDto);
        MemberDto editMember = memberService.getMember(loginMember.getMemberId());
        session.setAttribute("loginMember", editMember);
        return "redirect:/member/mypage";
    }

    @GetMapping("/editPasswd")
    public String editPasswd(@ModelAttribute EditPasswdForm editPasswdForm, Model model) {
        return "/member/editPasswd";
    }

    @PostMapping("/editPasswd")
    public String editPasswdAction(@ModelAttribute EditPasswdForm editPasswdForm, HttpSession session) {

        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");

        MemberDto memberDto = MemberDto.builder()
                .memberPasswd(editPasswdForm.getNewPasswd())
                .memberId(loginMember.getMemberId())
                .build();

        memberService.editPasswd(memberDto);

        session.invalidate();
        return "redirect:/member/login";
    }

    @PostMapping("/editPicture")
    public String editPictureAction(@ModelAttribute EditPictureForm editPictureForm, HttpSession session) {

        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
        UploadFile uploadFile = fileService.storeFile(editPictureForm.getProfileImage(), profileFileUploadPath);

        MemberDto memberDto = MemberDto.builder()
                .picture(uploadFile.getUploadFileName())
                .storePicture(uploadFile.getStoreFileName())
                .memberId(loginMember.getMemberId())
                .build();

        memberService.editPicture(memberDto);
        MemberDto editMember = memberService.getMember(loginMember.getMemberId());
        session.setAttribute("loginMember", editMember);
        return "/member/mypage";
    }

    // 회원 프로필 사진 요청 처리
    @GetMapping("/image/{profileFileName}")
    @ResponseBody
    public ResponseEntity<Resource> showImage(@PathVariable("profileFileName") String profileFileName) throws IOException {
        Path path = Paths.get(profileFileUploadPath + "/" + profileFileName);
        String contentType = Files.probeContentType(path);
        Resource resource = new FileSystemResource(path);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);
        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
    }

    @GetMapping("/searchMember")
    public String searchMember() {
        return "/member/searchMember";
    }

    @GetMapping("/searchId")
    public String searchId(@ModelAttribute SearchIdForm searchIdForm) {
        return "/member/searchId";
    }

    @PostMapping("/searchId")
    public String searchIdAction(@ModelAttribute SearchIdForm searchIdForm, Model model) {
        String searchId = memberService.searchId(searchIdForm.getSearchName(), searchIdForm.getSearchNickname());
        log.info("찾은 아이디 : {}", searchId);
        model.addAttribute("searchId", searchId);
        return "/member/searchIdResult";
    }

    @GetMapping("/searchIdResult")
    public String searchIdResult(Model model) {
        model.getAttribute("searchId");
        return "/member/searchIdResult";
    }

    @GetMapping("/searchPasswd")
    public String searchPasswd(@ModelAttribute SearchPasswdForm searchPasswdForm, Model model) {
        return "/member/searchPasswd";
    }



    @PostMapping("/searchPasswd")
    public String searchPasswdAction(@ModelAttribute SearchPasswdForm searchPasswdForm, RedirectAttributes redirectAttributes) throws MessagingException {
        MemberDto memberDto = memberService.searchPasswd(searchPasswdForm.getSearchId(), searchPasswdForm.getSearchName(), searchPasswdForm.getSearchEmail());

        if (memberDto != null) {
            UUID uuid = UUID.randomUUID();
            String tempPasswd = uuid.toString().substring(0, 6);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            messageHelper.setFrom(from);
            messageHelper.setTo(memberDto.getEmail());
            messageHelper.setSubject("[후반전 홈페이지] 임시 비밀번호 안내");

            StringBuilder body = new StringBuilder();
            body.append("요청하신 임시 비밀번호 수신을 위해 발송된 메일입니다. ")
                    .append("\n")
                    .append("임시 비밀번호는 ")
                    .append(tempPasswd)
                    .append("입니다.");
            messageHelper.setText(body.toString());
            javaMailSender.send(message);

            MemberDto editMemberPw = MemberDto.builder()
                    .memberPasswd(tempPasswd)
                    .memberId(memberDto.getMemberId())
                    .build();
            memberService.editPasswd(editMemberPw);

            redirectAttributes.addFlashAttribute("memberEmail", memberDto.getEmail());
        }
        return "redirect:/member/searchPasswdResult";
    }

    @GetMapping("/searchPasswdResult")
    public String searchPasswdResult() {
        return "/member/searchPasswdResult";
    }
}