package com.ezen.springmvc.web.meet.controller;

import com.ezen.springmvc.domain.meetArticle.dto.MeetArticleDto;
import com.ezen.springmvc.domain.meetArticle.dto.ReplyDto;
import com.ezen.springmvc.domain.meetArticle.service.MeetArticleService;
import com.ezen.springmvc.web.meet.form.MeetArticleForm;
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

    @GetMapping("/register")
    public String meetRegister() {
        return "/meet/meetRegister";
    }

//    목록
    @GetMapping
    public String meetList(Model model){
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
                .build();
        meetArticleDto.setCategoryId(3);
        meetArticleDto.setMemberId("sunday");
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
        model.addAttribute("meetArticle", meetArticleDto);
        model.addAttribute("replyList", replyList);
        log.info("수신한 댓글 목록 : {}", replyList);
        return "/meet/meetRead";
    }

//    댓글 등록
    @PostMapping("/read/{meetArticleId}")
    public String meetCreateReply(@ModelAttribute ReplyDto replyDto, @PathVariable("meetArticleId")
            int meetArticleId, RedirectAttributes redirectAttributes, Model model) {
        MeetArticleDto meetArticleDto = meetArticleService.readMeetArticle(3, meetArticleId);
        model.addAttribute("meetArticle", meetArticleDto);
        replyDto.setWriter("monday");
        log.info("수신한 댓글 : {}", replyDto);
        meetArticleService.createReply(replyDto);
        return "redirect:/meet/read/{meetArticleId}";
    }
}
