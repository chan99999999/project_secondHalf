package com.ezen.springmvc.web.meet.controller;

import com.ezen.springmvc.domain.meetArticle.service.MeetArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/meet")
@Slf4j
public class MeetController {

    private final MeetArticleService meetArticleService;

    public MeetController(MeetArticleService meetArticleService) {
        this.meetArticleService = meetArticleService;
    }

    @GetMapping
    public String meetList(){
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

    // 좋아요 기능 처리
//    @GetMapping("/like")
//    public @ResponseBody boolean handleHeart(
//            @RequestParam("dailyArticleId") int dailyArticleId,
//            @RequestParam("memberId") String memberId,
//            @RequestParam("checked") boolean checked) {
//        log.info("게시글 번호: {}, 회원 아이디 : {}, 조아요 체크 : {}", dailyArticleId, memberId, checked);
////        return meetArticleService.updateHeart(dailyArticleId, memberId, checked);
//    }
}
