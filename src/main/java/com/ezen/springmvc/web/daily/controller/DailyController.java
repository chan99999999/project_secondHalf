package com.ezen.springmvc.web.daily.controller;

import com.ezen.springmvc.domain.category.dto.CategoryDto;
import com.ezen.springmvc.domain.category.service.CategoryService;
import com.ezen.springmvc.domain.common.dto.UploadFile;
import com.ezen.springmvc.domain.common.service.FileService;
import com.ezen.springmvc.domain.dailyarticle.dto.*;
import com.ezen.springmvc.domain.dailyarticle.service.DailyArticleService;
import com.ezen.springmvc.domain.member.dto.MemberDto;
import com.ezen.springmvc.web.daily.form.DailyArticleForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.*;
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

    @Value("${upload.directory}") // 파일 저장 위치 지정 어노테이션
    private String profileFileUploadPath;

    private final DailyArticleService dailyArticleService;
    private final CategoryService categoryService;
    private final FileService fileService;

    @GetMapping("{categoryId}/register")
    public String dailyRegister() {
        return "/daily/dailyRegister";
    }

    // 신규 일상 게시글 등록 처리
    @PostMapping("{categoryId}/register")
    public String dailyRegister(@PathVariable("categoryId") int categoryId, @ModelAttribute DailyArticleForm dailyArticleForm, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        UploadFile uploadFile = fileService.storeFile(dailyArticleForm.getAttachImage(), profileFileUploadPath);

        HttpSession session = request.getSession();
        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");

        DailyArticleDto dailyArticleDto = DailyArticleDto.builder()
                .dailyArticleId(dailyArticleForm.getDailyArticleId())
                .memberId(loginMember.getMemberId())
                .title(dailyArticleForm.getTitle())
                .content(dailyArticleForm.getContent())
                .build();

        FileDto fileDto = FileDto.builder()
                .fileName(uploadFile.getUploadFileName())
                .encryptedName(uploadFile.getStoreFileName())
                .build();

        dailyArticleDto.setCategoryId(categoryId);
        log.info("수신한 게시글 정보 : {}", dailyArticleDto);
        DailyArticleDto createDailyArticleDto = dailyArticleService.writeDailyArticle(dailyArticleDto, fileDto);

        redirectAttributes.addFlashAttribute("createDailyArticleDto", createDailyArticleDto);
        redirectAttributes.addFlashAttribute("fileDto", fileDto);
        return "redirect:/daily/{categoryId}";
    }


    // 일상 게시글 목록 처리
    @GetMapping("{categoryId}")
    public String dailyList(@PathVariable("categoryId") int categoryId, Model model) {
//        List<CategoryDto> categoryList = dailyArticleService.getCategoryList();
        List<DailyArticleDto> dailyArticleList = dailyArticleService.getDailyArticles(categoryId);
        List<FileDto> fileList = dailyArticleService.getFiles();
        model.addAttribute("dailyArticleList", dailyArticleList);
//        model.addAttribute("categoryList", categoryList);
        for (DailyArticleDto dailyArticleDto : dailyArticleList) {
            log.info("수신한 게시글 목록 : {}", dailyArticleDto);
        }
        return "/daily/dailyList";
    }

    // 일상 게시글 상세보기 처리
    @GetMapping("{categoryId}/read/{dailyArticleId}")
    public String dailyRead(@PathVariable("categoryId") int categoryId, @PathVariable("dailyArticleId") int dailyArticleId, Model model, HttpServletRequest request) {
        log.info("게시글 번호 : {}", dailyArticleId);

        HttpSession session = request.getSession();
        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
        int heartCount = 0;

        if (loginMember != null) {
            heartCount = dailyArticleService.getHeartCount(dailyArticleId, loginMember.getMemberId());
        } else {
            heartCount = 0; // 로그인하지 않은 사용자는 좋아요를 누르지 않은 것으로 간주
        }
        DailyArticleDto dailyArticleDto = dailyArticleService.getDailyArticle(categoryId, dailyArticleId);
        FileDto fileDto = dailyArticleService.getFile(dailyArticleId);
        List<ReplyDto> replyList = dailyArticleService.getReplyList(dailyArticleId);
        int replyCount = dailyArticleService.getReplyCount(dailyArticleId);

        model.addAttribute("dailyArticle", dailyArticleDto);
        model.addAttribute("file", fileDto);
        model.addAttribute("replyList", replyList);
        model.addAttribute("heartCount", heartCount);
        model.addAttribute("replyCount", replyCount);

        if (loginMember != null) {
            model.addAttribute("loginMember", loginMember);
        }

//        model.addAttribute("loginMember", loginMember);
        log.info("수신한 댓글 목록 : {}", replyList);
        return "/daily/dailyRead";
    }

    // 댓글 등록 처리
    @PostMapping("{categoryId}/read/{dailyArticleId}")
    public String dailyReadReply(@PathVariable("categoryId") int categoryId, @ModelAttribute ReplyDto replyDto, @PathVariable("dailyArticleId") int dailyArticleId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");

        DailyArticleDto dailyArticleDto = dailyArticleService.getDailyArticle(categoryId, dailyArticleId);
        FileDto fileDto = dailyArticleService.getFile(dailyArticleId);
        model.addAttribute("dailyArticle", dailyArticleDto);
        model.addAttribute("file", fileDto);
        replyDto.setWriter(loginMember.getMemberId());
        log.info("수신한 댓글 : {}", replyDto);
        dailyArticleService.writeReply(replyDto);
        return "redirect:/daily/{categoryId}/read/{dailyArticleId}";
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
    public ResponseEntity<Map<String, Object>> handleHeart(
            @RequestParam("dailyArticleId") int dailyArticleId,
            @RequestParam("memberId") String memberId,
            @RequestParam("checked") boolean checked
            ) {
        log.info("게시글 번호: {}, 회원 아이디 : {}, 조아요 체크 : {}", dailyArticleId, memberId, checked);

        Map<String, Object> map = new HashMap<>();

        boolean isUpdated = dailyArticleService.insertAndUpdateHeart(dailyArticleId, memberId, checked);
        int totalHeartCount = dailyArticleService.getTotalHeartCount(dailyArticleId);

        map.put("totalHeartCount", totalHeartCount);
        map.put("isUpdated", isUpdated);


        return ResponseEntity.ok(map);
    }

    @GetMapping("/getLoginMember")
    public ResponseEntity<MemberDto> getLoginMember(HttpServletRequest request) {
        HttpSession session = request.getSession();
        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
        log.info("전달받은 로그인 회원 : {}", loginMember);

        return ResponseEntity.ok(loginMember);
    }

    @GetMapping("/getCategory")
    public String getCategory(Model model) {
        List<CategoryDto> categoryList = categoryService.getCategoryList();
        log.info("수신받은 카테고리 목록 : {}", categoryList);
        model.addAttribute("categoryList", categoryList);
        return "layout/template";
    }
}
