package com.ezen.springmvc.web.daily.controller;

import com.ezen.springmvc.domain.common.dto.UploadFile;
import com.ezen.springmvc.domain.common.service.FileService;
import com.ezen.springmvc.domain.dailyarticle.dto.DailyArticleDto;
import com.ezen.springmvc.domain.dailyarticle.dto.FileDto;
import com.ezen.springmvc.domain.dailyarticle.mapper.DailyArticleMapper;
import com.ezen.springmvc.domain.dailyarticle.service.DailyArticleService;
import com.ezen.springmvc.domain.dailyarticle.service.DailyArticleServiceImpl;
import com.ezen.springmvc.domain.member.dto.MemberDto;
import com.ezen.springmvc.domain.member.mapper.MemberMapper;
import com.ezen.springmvc.domain.member.service.MemberServiceImpl;
import com.ezen.springmvc.web.daily.form.DailyArticleForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/daily")
@Slf4j
@RequiredArgsConstructor
public class DailyController {

    @Value("${upload.dir}") // 파일 저장 위치 지정 어노테이션
    private String profileFileUploadPath;

    private final DailyArticleMapper dailyArticleMapper;
    private final DailyArticleService dailyArticleService;
    private final FileService fileService;

    @GetMapping("/register")
    public String dailyRegister() {
        return "/daily/dailyRegister";
    }

    // 신규 일상 게시글 등록 처리
    @PostMapping("/register")
    public String dailyRegister(@ModelAttribute DailyArticleForm dailyArticleForm, RedirectAttributes redirectAttributes) {
        UploadFile uploadFile = fileService.storeFile(dailyArticleForm.getAttachImage(), profileFileUploadPath);

        DailyArticleDto dailyArticleDto = DailyArticleDto.builder()
                .dailyArticleId(dailyArticleForm.getDailyArticleId())
                .title(dailyArticleForm.getTitle())
                .content(dailyArticleForm.getContent())
                .build();

        FileDto fileDto = FileDto.builder()
                .fileName(uploadFile.getUploadFileName())
                .encryptedName(uploadFile.getStoreFileName())
                .build();

        dailyArticleDto.setCategoryId(2);
        dailyArticleDto.setMemberId("sunday");
        log.info("수신한 게시글 정보 : {}", dailyArticleDto);
        DailyArticleDto createDailyArticleDto = dailyArticleService.writeDailyArticle(dailyArticleDto, fileDto);

        redirectAttributes.addFlashAttribute("createDailyArticleDto", createDailyArticleDto);
        redirectAttributes.addFlashAttribute("fileDto", fileDto);
        return "redirect:/daily";
    }

    // 일상 게시글 목록 처리
    @GetMapping
    public String dailyList(Model model) {
        List<DailyArticleDto> dailyArticleList = dailyArticleService.getDailyArticles(2);
        model.addAttribute("dailyArticleList", dailyArticleList);
        for (DailyArticleDto dailyArticleDto : dailyArticleList) {
            log.info("수신한 게시글 목록 : {}", dailyArticleDto);
        }
        return "/daily/dailyList";
    }

    // 일상 게시글 상세보기 처리
    @GetMapping("/read/{dailyArticleId}")
    public String dailyRead(@PathVariable("dailyArticleId") int dailyArticleId, Model model) {
        log.info("게시글 번호 : {}", dailyArticleId);
        DailyArticleDto dailyArticleDto = dailyArticleService.getDailyArticle(2, dailyArticleId);
        model.addAttribute("dailyArticle", dailyArticleDto);
        return "/daily/dailyRead";
    }
}
