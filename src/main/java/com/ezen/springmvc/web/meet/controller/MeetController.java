package com.ezen.springmvc.web.meet.controller;

import com.ezen.springmvc.domain.meetArticle.dto.*;
import com.ezen.springmvc.domain.category.service.CategoryService;
import com.ezen.springmvc.domain.meetArticle.service.MeetArticleService;
import com.ezen.springmvc.web.meet.form.MeetArticleForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/meet")
@Slf4j
@RequiredArgsConstructor
public class MeetController {

    private final MeetArticleService meetArticleService;
    private final CategoryService categoryService;

    @GetMapping("{categoryId}/register")
    public String meetRegister(@PathVariable("categoryId") int categoryId, Model model) {
        model.addAttribute("categoryId", categoryId);
        return "/meet/meetRegister";
    }

    //    목록
    @GetMapping("{categoryId}")
    public String meetList(@PathVariable("categoryId") int categoryId, @RequestParam(value = "tagName", required = false) String tagName, Model model) {
        List<MeetArticleDto> meetArticleList;
        if (tagName != null && !tagName.isEmpty()) {
            meetArticleList = meetArticleService.findByTagName(categoryId, tagName);
        } else {
            meetArticleList = meetArticleService.findByAllMeetArticle(categoryId);
        }
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("meetArticleList", meetArticleList);
        for (MeetArticleDto meetArticleDto : meetArticleList) {
            log.info("수신한 게시글 목록 : {}", meetArticleDto);
        }
        return "/meet/meetList";
    }

    //    등록
    @PostMapping("{categoryId}/register")
    public String meetRegister(@PathVariable("categoryId") int categoryId, @ModelAttribute MeetArticleForm meetArticleForm,
                               RedirectAttributes redirectAttributes, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
//        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
        MeetArticleDto meetArticleDto = MeetArticleDto.builder()
                .categoryId(categoryId)
                .meetArticleId(meetArticleForm.getMeetArticleId())
//                .memberId(loginMember.getMemberId())
                .title(meetArticleForm.getTitle())
                .content(meetArticleForm.getContent())
                .enter(meetArticleForm.getEnter())
                .time(meetArticleForm.getTime())
                .hitcount(meetArticleForm.getHitcount())
                .build();
        MeetArticleDto createMeetArticleDto = meetArticleService.createMeetArticle(meetArticleDto);
//        meetArticleDto.setPlaceId(1);
        String tags = meetArticleForm.getTags();
        if (tags != null && !tags.isEmpty()) {
            List<String> tagNames = Arrays.asList(tags.split(","));
            for (String tagName : tagNames) {
                tagName = tagName.trim();
                TagDto tagDto = TagDto.builder()
                        .tagName(tagName)
                        .build();
                meetArticleService.creatTag(tagDto);
                TagArticleDto tagArticleDto = TagArticleDto.builder()
                        .meetArticleId(meetArticleForm.getMeetArticleId())
                        .build();
                meetArticleService.createTagArticle(tagArticleDto);
            }
        }
        log.info("수신한 게시글 정보 : {}", meetArticleDto);
        redirectAttributes.addFlashAttribute("createMeetArticleDto", createMeetArticleDto);
        return "redirect:/meet/{categoryId}";
    }

    //    게시글 상세보기
    @GetMapping("{categoryId}/read/{meetArticleId}")
    public String meetRead(@PathVariable("categoryId") int categoryId, @PathVariable("meetArticleId") int meetArticleId, Model model, HttpServletRequest request) {
        log.info("게시글 번호 : {}", meetArticleId);
//        HttpSession session = request.getSession();
//        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
//        조회수 증가
        meetArticleService.hitcount(meetArticleId);
//        조회수가 증가된 게시글 반환
        MeetArticleDto meetArticleDto = meetArticleService.readMeetArticle(categoryId, meetArticleId);
        List<ReplyDto> replyList = meetArticleService.replyList(meetArticleId);
        int replyCount = meetArticleService.replyCount(meetArticleId);
        model.addAttribute("meetArticle", meetArticleDto);
        model.addAttribute("replyList", replyList);
        model.addAttribute("replyCount", replyCount);
//        if (loginMember != null) {
//            model.addAttribute("loginMember", loginMember);
//        }
        log.info("수신한 댓글 목록 : {}", replyList);
        return "/meet/meetRead";
    }

    //    댓글 등록
    @PostMapping("{categoryId}/read/{meetArticleId}")
    public String meetCreateReply(@PathVariable("categoryId") int categoryId, @ModelAttribute ReplyDto replyDto,
                                  @PathVariable("meetArticleId") int meetArticleId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
//        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
        MeetArticleDto meetArticleDto = meetArticleService.readMeetArticle(categoryId, meetArticleId);
        model.addAttribute("meetArticle", meetArticleDto);
//        replyForm.setWriter(loginMember.getMemberId());
        log.info("수신한 댓글 : {}", replyDto);
        meetArticleService.createReply(replyDto);
        return "redirect:/meet/{categoryId}/read/{meetArticleId}";
    }

//    @GetMapping("/getLoginMember")
//    public ResponseEntity<MemberDto> getLoginMember(HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
//        log.info("전달받은 로그인 회원 : {}", loginMember);
//
//        return ResponseEntity.ok(loginMember);
//    }
}
