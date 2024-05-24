package com.ezen.springmvc.web.daily.controller;

import com.ezen.springmvc.domain.common.dto.UploadFile;
import com.ezen.springmvc.domain.common.service.FileService;
import com.ezen.springmvc.domain.dailyarticle.dto.*;
import com.ezen.springmvc.domain.dailyarticle.mapper.DailyArticleMapper;
import com.ezen.springmvc.domain.dailyarticle.mapper.HeartMapper;
import com.ezen.springmvc.domain.dailyarticle.service.DailyArticleService;
import com.ezen.springmvc.domain.dailyarticle.service.DailyArticleServiceImpl;
import com.ezen.springmvc.domain.member.dto.MemberDto;
import com.ezen.springmvc.domain.member.mapper.MemberMapper;
import com.ezen.springmvc.domain.member.service.MemberServiceImpl;
import com.ezen.springmvc.web.daily.form.DailyArticleForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/daily")
@Slf4j
@RequiredArgsConstructor
public class DailyController {

    private final HeartMapper heartMapper;
    @Value("${upload.dir}") // 파일 저장 위치 지정 어노테이션
    private String profileFileUploadPath;

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
    @GetMapping()
    public String dailyList(Model model) {
//        List<CategoryDto> categoryList = dailyArticleService.getCategoryList();
        List<DailyArticleDto> dailyArticleList = dailyArticleService.getDailyArticles(2);
        List<FileDto> fileList = dailyArticleService.getFiles();
        model.addAttribute("dailyArticleList", dailyArticleList);
//        model.addAttribute("categoryList", categoryList);
        for (DailyArticleDto dailyArticleDto : dailyArticleList) {
            log.info("수신한 게시글 목록 : {}", dailyArticleDto);
        }
        return "/daily/dailyList";
    }

    // 일상 게시글 상세보기 처리
    @GetMapping("/read/{dailyArticleId}")
    public String dailyRead(@PathVariable("dailyArticleId") int dailyArticleId, Model model, RedirectAttributes redirectAttributes) {
        log.info("게시글 번호 : {}", dailyArticleId);
        DailyArticleDto dailyArticleDto = dailyArticleService.getDailyArticle(2, dailyArticleId);
        FileDto fileDto = dailyArticleService.getFile(dailyArticleId);
        List<ReplyDto> replyList = dailyArticleService.getReplyList(dailyArticleId);
        model.addAttribute("dailyArticle", dailyArticleDto);
        model.addAttribute("file", fileDto);
        model.addAttribute("replyList", replyList);
        log.info("수신한 댓글 목록 : {}", replyList);
        return "/daily/dailyRead";
    }

    // 댓글 등록 처리
    @PostMapping("/read/{dailyArticleId}")
    public String dailyReadReply(@ModelAttribute ReplyDto replyDto, @PathVariable("dailyArticleId") int dailyArticleId, RedirectAttributes redirectAttributes, Model model) {
        DailyArticleDto dailyArticleDto = dailyArticleService.getDailyArticle(2, dailyArticleId);
        FileDto fileDto = dailyArticleService.getFile(dailyArticleId);
        model.addAttribute("dailyArticle", dailyArticleDto);
        model.addAttribute("file", fileDto);
        replyDto.setWriter("monday");
        log.info("수신한 댓글 : {}", replyDto);
        dailyArticleService.writeReply(replyDto);
        return "redirect:/daily/read/{dailyArticleId}";
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

    // 좋아요 기능 처리
    @GetMapping("/like")
    public @ResponseBody boolean handleHeart(
            @RequestParam("dailyArticleId") int dailyArticleId,
            @RequestParam("memberId") String memberId,
            @RequestParam("checked") boolean checked) {
        log.info("게시글 번호: {}, 회원 아이디 : {}, 조아요 체크 : {}", dailyArticleId, memberId, checked);
        return dailyArticleService.updateHeart(dailyArticleId, memberId, checked);
    }
}
