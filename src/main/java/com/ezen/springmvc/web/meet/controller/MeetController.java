package com.ezen.springmvc.web.meet.controller;

import com.ezen.springmvc.domain.category.dto.CategoryDto;
import com.ezen.springmvc.domain.common.dto.SearchDto;
import com.ezen.springmvc.domain.dailyarticle.dto.DailyArticleDto;
import com.ezen.springmvc.domain.dailyarticle.dto.FileDto;
import com.ezen.springmvc.domain.dailyarticle.dto.ReplyDto;
import com.ezen.springmvc.domain.meetArticle.dto.*;
import com.ezen.springmvc.domain.category.service.CategoryService;
import com.ezen.springmvc.domain.meetArticle.service.MeetArticleService;
import com.ezen.springmvc.domain.member.dto.MemberDto;
import com.ezen.springmvc.web.common.page.Pagination;
import com.ezen.springmvc.web.common.page.ParameterForm;
import com.ezen.springmvc.web.daily.form.DailyArticleForm;
import com.ezen.springmvc.web.meet.form.MeetArticleForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
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
    public String meetList(@PathVariable("categoryId") int categoryId,
                           @ModelAttribute ParameterForm parameterForm,
                           Model model) {
        log.info("전달된 파라미터 정보 : {}", parameterForm);
        log.info("제목 검색 파라미터 정보 : {}", parameterForm.getTitle());
        SearchDto searchDto = SearchDto.builder()
                .limit(parameterForm.getElementSize())
                .page(parameterForm.getRequestPage())
                .tagName(parameterForm.getTagName())
                .meetArticleId(parameterForm.getMeetArticleId())
                .title(parameterForm.getTitle())
                .build();
        List<MeetArticleDto> meetArticleList;
        // 태그 검색일 경우
        if (parameterForm.getTagName() != null && !parameterForm.getTagName().isEmpty()) {
            meetArticleList = new ArrayList<>();
            List<TagDto> tagList = meetArticleService.findByAllTagName(parameterForm.getTagName());
            if (!tagList.isEmpty()) {
                tagList.forEach(tagDto -> {
                    MeetArticleDto meetArticleDto = MeetArticleDto.builder()
                            .meetArticleId(tagDto.getMeetArticleId())
                            .title(tagDto.getTitle())
                            .content(tagDto.getContent())
                            .regdate(tagDto.getRegdate())
                            .time(tagDto.getTime())
                            .tagName(parameterForm.getTagName())
                            .build();
                    meetArticleList.add(meetArticleDto);
                });
            }

        } else if (parameterForm.getTitle() != null) { // 제목 검색일 경우..
            meetArticleList = meetArticleService.findByTitle(categoryId, searchDto);
        } else {
            meetArticleList = meetArticleService.findByAllMeetArticle(categoryId);
        }
        TagDto tagList = TagDto.builder()
                .tagName(parameterForm.getTagName())
                .build();
        for (MeetArticleDto meetArticleDto : meetArticleList) {
            parameterForm.getTagName();
            log.info("수신한 게시글 목록 : {}", meetArticleDto);
        }
        // 페이징 처리를 위한 테이블 행의 개수 조회
        int selectedRowCount = meetArticleService.findByMeetArticleCount(categoryId, searchDto);
        log.info("조회된 행의 수: {} ", selectedRowCount);
        parameterForm.setRowCount(selectedRowCount);
        Pagination pagination = new Pagination(parameterForm);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("meetArticleList", meetArticleList);
        model.addAttribute("parameterForm", parameterForm);
        model.addAttribute("pagination", pagination);
        model.addAttribute("tagList", tagList);
        return "/meet/meetList";
    }

    //    등록
    @PostMapping("{categoryId}/register")
    public String meetRegister(@PathVariable("categoryId") int categoryId,
                               @ModelAttribute MeetArticleForm meetArticleForm,
                               HttpServletRequest request) {
        log.info("수신한 정보들 : {}", meetArticleForm);
        HttpSession session = request.getSession();
        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
        MeetArticleDto meetArticleDto = MeetArticleDto.builder()
                .categoryId(categoryId)
                .meetArticleId(meetArticleForm.getMeetArticleId())
                .memberId(loginMember.getMemberId())
                .placeId(1) // 추후 동적 변경 필요
                .title(meetArticleForm.getTitle())
                .content(meetArticleForm.getContent())
                .enter(meetArticleForm.getEnter())
                .time(meetArticleForm.getTime())
                .hitcount(meetArticleForm.getHitcount())
                .tags(meetArticleForm.getTags())
                .build();
        meetArticleService.addMeet(meetArticleDto);
        return "redirect:/meet/{categoryId}";
    }

    //    게시글 상세보기
    @GetMapping("/read/{meetArticleId}")
    public String meetRead(@PathVariable("meetArticleId") int meetArticleId,
                           HttpServletRequest request,
                           Model model) {
        log.info("게시글 번호 : {}", meetArticleId);
        HttpSession session = request.getSession();
        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
        MeetArticleDto meetArticleDto = meetArticleService.readMeetArticle(3, meetArticleId);
        meetArticleService.hitcount(meetArticleDto);
        List<MeetReplyDto> replyList = meetArticleService.replyList(meetArticleId);
        int replyCount = meetArticleService.replyCount(meetArticleId);
        model.addAttribute("meetArticleDto", meetArticleDto);
        model.addAttribute("replyList", replyList);
        model.addAttribute("replyCount", replyCount);
        if (loginMember != null) {
            model.addAttribute("loginMember", loginMember);
        }
        log.info("수신한 댓글 목록 : {}", replyList);
        return "/meet/meetRead";
    }

    //    댓글 등록
    @PostMapping("/read/{meetArticleId}")
    public String meetCreateReply(@ModelAttribute MeetReplyDto meetReplyDto,
                                  @PathVariable("meetArticleId") int meetArticleId,
                                  Model model,
                                  HttpServletRequest request) {
        HttpSession session = request.getSession();
        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
        MeetArticleDto meetArticleDto = meetArticleService.readMeetArticle(3, meetArticleId);
        model.addAttribute("meetArticleDto", meetArticleDto);
        meetReplyDto.setWriter(loginMember.getMemberId());
        log.info("수신한 댓글 : {}", meetReplyDto);
        meetArticleService.createReply(meetReplyDto);
        return "redirect:/meet/read/{meetArticleId}";
    }

//    참여하기
//    @PostMapping("/{categoryId}/participate/{meetArticleId}")
//    @ResponseBody
//    public ResponseEntity<?> participate(@PathVariable int categoryId, @PathVariable int meetArticleId, @RequestBody String memberId) {
//        meetArticleService.participate(categoryId, meetArticleId, memberId);
//        int currentParticipants = meetArticleService.getParticipantCount(meetArticleId);
//        return ResponseEntity.ok(currentParticipants);
//    }
//
////    참여취소
//    @PostMapping("/{categoryId}/cancelParticipation/{meetArticleId}")
//    @ResponseBody
//    public ResponseEntity<?> cancelParticipation(@PathVariable int categoryId, @PathVariable int meetArticleId, @RequestBody String memberId) {
//        meetArticleService.cancelParticipation(categoryId, meetArticleId, memberId);
//        int currentParticipants = meetArticleService.getParticipantCount(meetArticleId);
//        return ResponseEntity.ok(currentParticipants);
//    }

    @GetMapping("/getLoginMember")
    public ResponseEntity<MemberDto> getLoginMember(HttpServletRequest request) {
        HttpSession session = request.getSession();
        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
        log.info("전달받은 로그인 회원 : {}", loginMember);
        return ResponseEntity.ok(loginMember);
    }

    @GetMapping("/category")
    public String getCategory(Model model) {
        List<CategoryDto> categoryList = categoryService.getCategoryList();
        log.info("수신받은 카테고리 목록 : {}", categoryList);
        model.addAttribute("categoryList", categoryList);
        return "layout/template";
    }
}
