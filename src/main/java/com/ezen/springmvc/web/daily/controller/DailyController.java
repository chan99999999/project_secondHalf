package com.ezen.springmvc.web.daily.controller;

import com.ezen.springmvc.domain.dailyarticle.dto.DailyArticleDto;
import com.ezen.springmvc.domain.dailyarticle.mapper.DailyArticleMapper;
import com.ezen.springmvc.domain.dailyarticle.service.DailyArticleService;
import com.ezen.springmvc.domain.dailyarticle.service.DailyArticleServiceImpl;
import com.ezen.springmvc.domain.member.dto.MemberDto;
import com.ezen.springmvc.domain.member.mapper.MemberMapper;
import com.ezen.springmvc.domain.member.service.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/daily")
@Slf4j
public class DailyController {

    private final DailyArticleMapper dailyArticleMapper;
    private final DailyArticleService dailyArticleService;

    public DailyController(DailyArticleMapper dailyArticleMapper, DailyArticleService dailyArticleService) {
        this.dailyArticleMapper = dailyArticleMapper;
        this.dailyArticleService = dailyArticleService;
    }



    @GetMapping("/register")
    public String dailyRegister(){
        return "/daily/dailyRegister";
    }

    // 신규 일상 게시글 등록 처리
    @PostMapping("/register")
    public String dailyRegister(@ModelAttribute("DailyArticleDto")DailyArticleDto dailyArticleDto, RedirectAttributes redirectAttributes){
        dailyArticleDto.setCategoryId(2);
        dailyArticleDto.setMemberId("sunday");
        log.info("수신한 게시글 정보 : {}", dailyArticleDto);
        DailyArticleDto createDailyArticleDto = dailyArticleService.writeDailyArticle(dailyArticleDto);

        redirectAttributes.addFlashAttribute("createDailyArticleDto", createDailyArticleDto);
        return "redirect:/daily";
    }

    // 일상 게시글 목록 처리
    @GetMapping
    public String dailyList(Model model) {
        List<DailyArticleDto> dailyArticleList = dailyArticleService.getDailyArticles(2);
        model.addAttribute("dailyArticleList", dailyArticleList);
        return "/daily/dailyList";
    }
    
    @GetMapping("/read")
    public String dailyRead(){
        return "/daily/dailyRead";
    }
}
