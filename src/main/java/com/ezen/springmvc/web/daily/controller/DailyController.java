package com.ezen.springmvc.web.daily.controller;

import com.ezen.springmvc.domain.category.dto.CategoryDto;
import com.ezen.springmvc.domain.category.service.CategoryService;
import com.ezen.springmvc.domain.common.dto.SearchDto;
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
import com.ezen.springmvc.web.common.page.Pagination;
import com.ezen.springmvc.web.common.page.ParameterForm;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller
@RequestMapping("/daily")
@Slf4j
@RequiredArgsConstructor
public class DailyController {

    // 첨부 파일 저장 위치 지정 어노테이션
    @Value("${upload.directory}")
    private String profileFileUploadPath;

    private final DailyArticleService dailyArticleService;
    private final FileService fileService;

    // 일상 게시글 작성 화면 요청 처리
    @GetMapping("{categoryId}/register")
    public String dailyRegister(@PathVariable("categoryId") int categoryId, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        HttpSession session = request.getSession();
        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");

        if (loginMember == null) {
            redirectAttributes.addFlashAttribute("categoryId", categoryId);
            return "redirect:/member/login";
        }

        model.addAttribute("categoryId", categoryId);
        return "/daily/dailyRegister";
    }

    // 일상 게시글 작성 요청 처리
    @PostMapping("{categoryId}/register")
    public String dailyRegister(@PathVariable("categoryId") int categoryId,
                                @ModelAttribute DailyArticleForm dailyArticleForm,
                                RedirectAttributes redirectAttributes,
                                HttpServletRequest request) {

        HttpSession session = request.getSession();
        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");

        // 파일
        List<MultipartFile> attachImages = dailyArticleForm.getAttachImages();
        List<UploadFile> uploadFiles = fileService.storeFiles(attachImages, profileFileUploadPath);
        List<FileDto> fileList = new ArrayList<>();
        for (UploadFile uploadFile : uploadFiles) {
            FileDto fileDto = FileDto.builder()
                    .dailyArticleId(dailyArticleForm.getDailyArticleId())
                    .fileName(uploadFile.getUploadFileName())
                    .encryptedName(uploadFile.getStoreFileName())
                    .build();

            fileList.add(fileDto);
        }

        // 일상 게시글
        DailyArticleDto dailyArticleDto = DailyArticleDto.builder()
                .categoryId(categoryId)
                .dailyArticleId(dailyArticleForm.getDailyArticleId())
                .memberId(loginMember.getMemberId())
                .title(dailyArticleForm.getTitle())
                .content(dailyArticleForm.getContent())
                .tagNames(dailyArticleForm.getTags())
                .build();

        // 일상 게시글, 파일 등록
        DailyArticleDto createDailyArticleDto = dailyArticleService.writeDailyArticle(dailyArticleDto, fileList);

        // 태그
        String tags = dailyArticleForm.getTags();
        if (tags != null && !tags.isEmpty()) {
            // 콤마를 기준으로 태그 이름들 파싱
            List<String> tagNames = Arrays.asList(tags.split(","));
            for (String tagName : tagNames) {
                tagName = tagName.trim();

                // 태그 등록
                TagDto tagDto = dailyArticleService.getOrCreateTag(tagName);

                // 태그 게시글 등록
                dailyArticleService.getTagArticle(tagDto.getTagId(), dailyArticleDto.getDailyArticleId());
            }
        }

        redirectAttributes.addFlashAttribute("createDailyArticleDto", createDailyArticleDto);
        redirectAttributes.addFlashAttribute("fileList", fileList);
        return "redirect:/daily/{categoryId}";
    }

    // 일상 게시글 목록 요청 처리
    @GetMapping("{categoryId}")
    public String dailyList(@PathVariable("categoryId") int categoryId,
                            @ModelAttribute ParameterForm parameterForm,
                            Model model) {

        List<DailyArticleDto> dailyArticleList = null;

        // 검색 조건
        SearchDto searchDto = SearchDto.builder()
                .limit(parameterForm.getElementSize())
                .page(parameterForm.getRequestPage())
                .tagName(parameterForm.getTagName())
                .build();

        // 태그 이름 파라미터 유무에 따른 게시글 목록 반환
        if (parameterForm.getTagName() != null && !parameterForm.getTagName().isEmpty()) {
            dailyArticleList = dailyArticleService.getDailyArticlesByTagName(categoryId, parameterForm.getTagName(), searchDto);
        } else {
            dailyArticleList = dailyArticleService.getDailyArticles(categoryId, searchDto);
        }

        // 페이징 처리를 위한 테이블 행의 개수 조회
        int selectedRowCount = dailyArticleService.getDailyArticleCount(categoryId, searchDto);
        parameterForm.setRowCount(selectedRowCount);
        Pagination pagination = new Pagination(parameterForm);

        model.addAttribute("categoryId", categoryId);
        model.addAttribute("dailyArticleList", dailyArticleList);
        model.addAttribute("parameterForm", parameterForm);
        model.addAttribute("pagination", pagination);
        return "/daily/dailyList";
    }

    // 일상 게시글 상세보기 요청 처리
    @GetMapping("{categoryId}/read/{dailyArticleId}")
    public String dailyRead(@PathVariable("categoryId") int categoryId, @PathVariable("dailyArticleId") int dailyArticleId, Model model, HttpServletRequest request) {

        HttpSession session = request.getSession();
        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");

        int heartCount = 0;

        if (loginMember != null) {
            heartCount = dailyArticleService.getHeartCount(dailyArticleId, loginMember.getMemberId());
        } else {
            heartCount = 0; // 로그인하지 않은 사용자는 좋아요를 누르지 않은 것으로 간주
        }

        DailyArticleDto dailyArticleDto = dailyArticleService.getDailyArticle(categoryId, dailyArticleId);
        List<FileDto> fileList = dailyArticleService.getFiles(dailyArticleId);
        List<ReplyDto> replyList = dailyArticleService.getReplyList(dailyArticleId);
        int replyCount = dailyArticleService.getReplyCount(dailyArticleId);

        if (loginMember != null) {
            model.addAttribute("loginMember", loginMember);
        }
        model.addAttribute("dailyArticle", dailyArticleDto);
        model.addAttribute("fileList", fileList);
        model.addAttribute("replyList", replyList);
        model.addAttribute("heartCount", heartCount);
        model.addAttribute("replyCount", replyCount);
        return "/daily/dailyRead";
    }

    // 댓글 등록 요청 처리(서버 사이드 렌더링)
//    @PostMapping("{categoryId}/read/{dailyArticleId}")
//    public String dailyReadReply(@PathVariable("categoryId") int categoryId, @ModelAttribute ReplyDto replyDto, @PathVariable("dailyArticleId") int dailyArticleId, Model model, HttpServletRequest request) {
//
//        HttpSession session = request.getSession();
//        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
//
//        replyDto.setWriter(loginMember.getMemberId());
//        dailyArticleService.writeReply(replyDto);
//
//        DailyArticleDto dailyArticleDto = dailyArticleService.getDailyArticle(categoryId, dailyArticleId);
//        List<FileDto> fileList = dailyArticleService.getFiles(dailyArticleId);
//
//        model.addAttribute("dailyArticle", dailyArticleDto);
//        model.addAttribute("fileList", fileList);
//        return "redirect:/daily/{categoryId}/read/{dailyArticleId}";
//    }

    // 댓글 등록 요청 api(클라이언트 사이드 렌더링)
    @PostMapping("{categoryId}/read/{dailyArticleId}")
    public ResponseEntity<ReplyDto> registerReply(@PathVariable("categoryId") int categoryId,
                                                  @PathVariable("dailyArticleId") int dailyArticleId,
                                                  @RequestBody ReplyDto replyDto) {

        replyDto.setDailyArticleId(dailyArticleId);
        dailyArticleService.writeReply(replyDto);

        return ResponseEntity.ok(replyDto);
    }

    // 좋아요 기능 api
    @GetMapping("/like")
    public ResponseEntity<Map<String, Object>> handleHeart(
            @RequestParam("dailyArticleId") int dailyArticleId,
            @RequestParam("memberId") String memberId,
            @RequestParam("checked") boolean checked) {

        Map<String, Object> map = new HashMap<>();

        boolean isUpdated = dailyArticleService.insertAndUpdateHeart(dailyArticleId, memberId, checked);
        int totalHeartCount = dailyArticleService.getTotalHeartCount(dailyArticleId);

        map.put("isUpdated", isUpdated);
        map.put("totalHeartCount", totalHeartCount);

        return ResponseEntity.ok(map);
    }

    // 게시글 수정 화면 요청 처리
    @GetMapping("{categoryId}/edit/{dailyArticleId}")
    public String dailyEditForm(@PathVariable("categoryId") int categoryId,
                                @PathVariable("dailyArticleId") int dailyArticleId,
                                Model model) {

        DailyArticleDto dailyArticleDto = dailyArticleService.getDailyArticle(categoryId, dailyArticleId);
        model.addAttribute("dailyArticleDto", dailyArticleDto);

        return "/daily/dailyEdit";
    }

    // 게시글 수정 요청 처리
    @PostMapping("{categoryId}/edit/{dailyArticleId}")
    public String dailyEditAction(@PathVariable("categoryId") int categoryId,
                                  @PathVariable("dailyArticleId") int dailyArticleId,
                                  @ModelAttribute DailyArticleForm dailyArticleForm) {

        DailyArticleDto editedDailyArticle = DailyArticleDto.builder()
                .title(dailyArticleForm.getTitle())
                .content(dailyArticleForm.getContent())
                .build();

        dailyArticleService.editDailyArticle(dailyArticleId, editedDailyArticle);

        return "redirect:/daily/{categoryId}";
    }

    // 게시글 삭제 처리
    @PostMapping("{categoryId}/delete/{dailyArticleId}")
    public String dailyDelete(@PathVariable("categoryId") int categoryId,
                              @PathVariable("dailyArticleId") int dailyArticleId) {

        dailyArticleService.removeDailyArticle(categoryId, dailyArticleId);

        return "redirect:/daily/{categoryId}";
    }

    // 댓글 삭제 요청 처리(서버 사이드 렌더링)
//    @PostMapping("{categoryId}/delete-reply/{dailyArticleId}/{replyId}")
//    public String dailyReplyDelete(@PathVariable("categoryId") int categoryId,
//                                   @PathVariable("dailyArticleId") int dailyArticleId,
//                                   @PathVariable("replyId") int replyId) {
//
//        dailyArticleService.removeReply(dailyArticleId, replyId);
//
//        return "redirect:/daily/{categoryId}/read/{dailyArticleId}";
//    }

    // 댓글 삭제 요청 api(클라이언트 사이드 렌더링)
    @PostMapping("/delete-reply")
    public ResponseEntity<Void> removeReply(@RequestBody ReplyDto replyDto) {

        dailyArticleService.removeReply(replyDto.getDailyArticleId(), replyDto.getReplyId());

        return ResponseEntity.ok().build();
    }

    // 댓글 수정 요청 api
    @PostMapping("/edit-reply")
    public ResponseEntity<Void> dailyEditReply(@RequestBody ReplyDto replyDto) {

        dailyArticleService.editReply(replyDto.getDailyArticleId(), replyDto.getReplyId(), replyDto.getContent());

        return ResponseEntity.ok().build();
    }

    // 세션에 저장된 회원 api
    @GetMapping("/getLoginMember")
    public ResponseEntity<MemberDto> getLoginMember(HttpServletRequest request) {

        HttpSession session = request.getSession();
        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");

        return ResponseEntity.ok(loginMember);
    }

    // 댓글 목록 api
    @GetMapping("/getreplylist/{dailyArticleId}")
    public ResponseEntity<List<ReplyDto>> getReplyList(@PathVariable("dailyArticleId") int dailyArticleId) {

        List<ReplyDto> replyList = dailyArticleService.getReplyList(dailyArticleId);

        return ResponseEntity.ok(replyList);
    }

    // 댓글 개수 api
    @GetMapping("reply-count/{dailyArticleId}")
    public ResponseEntity<Integer> replyCount(@PathVariable("dailyArticleId") int dailyArticleId) {

        int replyCount = dailyArticleService.getReplyCount(dailyArticleId);

        return ResponseEntity.ok(replyCount);
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
}
