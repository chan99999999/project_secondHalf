package com.ezen.springmvc.web.meet.controller;

import com.ezen.springmvc.domain.meetArticle.dto.CategoryDto;
import com.ezen.springmvc.domain.meetArticle.dto.MeetArticleDto;
import com.ezen.springmvc.domain.meetArticle.dto.ReplyDto;
import com.ezen.springmvc.domain.meetArticle.service.CategoryService;
import com.ezen.springmvc.domain.meetArticle.service.MeetArticleService;
import com.ezen.springmvc.web.meet.form.MeetArticleForm;
import com.ezen.springmvc.web.meet.form.ReplyForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/meet")
@Slf4j
@RequiredArgsConstructor
public class MeetController {

    private final MeetArticleService meetArticleService;
    private final CategoryService categoryService;

    @GetMapping("/register")
    public String meetRegister() {
        return "/meet/meetRegister";
    }

//    @GetMapping("/category")
//    public String category(Model model) {
//        List<CategoryDto> categoryList = categoryService.findByCategoryAll();
//        log.info("수신받은 카테고리 목록 : {}", categoryList);
//        model.addAttribute("categoryList", categoryList);
//        return "layout/template";
//    }

    //    목록
    @GetMapping()
    public String meetList(Model model) {
        List<MeetArticleDto> meetArticleList = meetArticleService.findByAllMeetArticle(3);
        model.addAttribute("meetArticleList", meetArticleList);
        for (MeetArticleDto meetArticleDto : meetArticleList) {
            log.info("수신한 게시글 목록 : {}", meetArticleDto);
        }
        return "/meet/meetList";
    }

    //    등록
    @PostMapping("/register")
    public String meetRegister(@ModelAttribute MeetArticleForm meetArticleForm, RedirectAttributes redirectAttributes) {
        MeetArticleDto meetArticleDto = MeetArticleDto.builder()
                .meetArticleId(meetArticleForm.getMeetArticleId())
                .title(meetArticleForm.getTitle())
                .content(meetArticleForm.getContent())
                .enter(meetArticleForm.getEnter())
                .time(meetArticleForm.getTime())
                .hitcount(meetArticleForm.getHitcount())
                .build();
        meetArticleDto.setCategoryId(3);
        meetArticleDto.setMemberId("sunday");
        meetArticleDto.setPlaceId(1);
        log.info("수신한 게시글 정보 : {}", meetArticleDto);
        MeetArticleDto createMeetArticleDto = meetArticleService.createMeetArticle(meetArticleDto);
        redirectAttributes.addFlashAttribute("createMeetArticleDto", createMeetArticleDto);
        return "redirect:/meet";
    }

    //    게시글 상세보기
    @GetMapping("/read/{meetArticleId}")
    public String meetRead(@PathVariable("meetArticleId") int meetArticleId, Model model) {
        log.info("게시글 번호 : {}", meetArticleId);
        MeetArticleDto meetArticleDto = meetArticleService.readMeetArticle(3, meetArticleId);
        List<ReplyDto> replyList = meetArticleService.replyList(meetArticleId);
        int replyCount = meetArticleService.replyCount(meetArticleId);
        for (ReplyDto reply : replyList) {
            log.info("댓글: {}", reply);
        }
        model.addAttribute("meetArticle", meetArticleDto);
        model.addAttribute("replyList", replyList);
        model.addAttribute("replyCount", replyCount);
        log.info("수신한 댓글 목록 : {}", replyList);
        return "/meet/meetRead";
    }

    //    댓글 등록
    @PostMapping("/read/{meetArticleId}")
    public String meetCreateReply(@ModelAttribute ReplyForm replyForm,RedirectAttributes redirectAttributes) {
        ReplyDto replyDto = ReplyDto.builder()
                .meetArticleId(replyForm.getMeetArticleId())
//                .writer(replyForm.getWriter())
                .content(replyForm.getContent())
                .build();
        replyDto.setWriter("tuesday");
        log.info("수신한 댓글 : {}", replyForm);
        MeetArticleDto createReply = meetArticleService.createReply(replyDto);
        redirectAttributes.addFlashAttribute("createReply", createReply);
        return "redirect:/meet/read/{meetArticleId}";
    }
}
