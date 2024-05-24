package com.ezen.springmvc.web.meet.controller;

import com.ezen.springmvc.domain.meetArticle.dto.MeetArticleDto;
import com.ezen.springmvc.domain.meetArticle.service.MeetArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/meet")
@Slf4j
@RequiredArgsConstructor
public class MeetController {

    private final MeetArticleService meetArticleService;

    @GetMapping
    public String meetList(Model model){
        List<MeetArticleDto> meetArticleList = meetArticleService.findByAllMeetArticle(3);
        model.addAttribute("meetArticleList", meetArticleList);
        for (MeetArticleDto meetArticleDto : meetArticleList) {
            log.info("수신한 게시글 목록 : {}", meetArticleDto);
        }
        return "/meet/meetList";
    }

    @GetMapping("/register")
    public String meetRegister(){
        return "/meet/meetRegister";
    }

    @GetMapping("/read")
    public String meetRead(){
        return "/meet/meetRead";
    }
}
