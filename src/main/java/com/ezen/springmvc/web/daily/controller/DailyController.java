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

    private final DailyArticleMapper dailyArticleMapper;
    @Value("${upload.directory}") // 파일 저장 위치 지정 어노테이션
    private String profileFileUploadPath;

    private final DailyArticleService dailyArticleService;
    private final CategoryService categoryService;
    private final FileService fileService;

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

    // 신규 일상 게시글 등록 처리
//    @PostMapping("{categoryId}/register")
//    public String dailyRegister(@PathVariable("categoryId") int categoryId, @ModelAttribute DailyArticleForm dailyArticleForm, RedirectAttributes redirectAttributes, HttpServletRequest request, Model model) {
//        UploadFile uploadFile = fileService.storeFile(dailyArticleForm.getAttachImage(), profileFileUploadPath);
//
//        HttpSession session = request.getSession();
//        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
//
//        DailyArticleDto dailyArticleDto = DailyArticleDto.builder()
//                .categoryId(categoryId)
//                .dailyArticleId(dailyArticleForm.getDailyArticleId())
//                .memberId(loginMember.getMemberId())
//                .title(dailyArticleForm.getTitle())
//                .content(dailyArticleForm.getContent())
//                .build();
//
//        FileDto fileDto = FileDto.builder()
//                .dailyArticleId(dailyArticleForm.getDailyArticleId())
//                .fileName(uploadFile.getUploadFileName())
//                .encryptedName(uploadFile.getStoreFileName())
//                .build();
//
//        DailyArticleDto createDailyArticleDto = dailyArticleService.writeDailyArticle(dailyArticleDto, fileDto);
//
//        String tags = dailyArticleForm.getTags();
//        if (tags != null && !tags.isEmpty()) {
//            List<String> tagNames = Arrays.asList(tags.split(","));
//            for (String tagName : tagNames) {
//                tagName = tagName.trim();
//                TagDto tagDto = TagDto.builder()
//                        .tagName(tagName)
//                        .build();
//                dailyArticleService.getTag(tagDto);
//
//                TagArticleDto tagArticleDto = TagArticleDto.builder()
//                        .dailyArticleId(dailyArticleForm.getDailyArticleId())
//                        .build();
//                dailyArticleService.getTagArticle(tagArticleDto);
//            }
//        }
//
//
////        dailyArticleDto.setCategoryId(categoryId);
//        log.info("수신한 게시글 정보 : {}", dailyArticleDto);
//        redirectAttributes.addFlashAttribute("createDailyArticleDto", createDailyArticleDto);
//        redirectAttributes.addFlashAttribute("fileDto", fileDto);
//        return "redirect:/daily/{categoryId}";
//    }

    @PostMapping("{categoryId}/register")
    public String dailyRegister(@PathVariable("categoryId") int categoryId,
                                @ModelAttribute DailyArticleForm dailyArticleForm,
                                RedirectAttributes redirectAttributes,
                                HttpServletRequest request,
                                Model model) {
        List<MultipartFile> attachImages = dailyArticleForm.getAttachImages();

        log.info("첨부 파일 목록들 : {}", attachImages);

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

        HttpSession session = request.getSession();
        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");

        DailyArticleDto dailyArticleDto = DailyArticleDto.builder()
                .categoryId(categoryId)
                .dailyArticleId(dailyArticleForm.getDailyArticleId())
                .memberId(loginMember.getMemberId())
                .title(dailyArticleForm.getTitle())
                .content(dailyArticleForm.getContent())
                .tagNames(dailyArticleForm.getTags())
                .build();

        DailyArticleDto createDailyArticleDto = dailyArticleService.writeDailyArticle(dailyArticleDto, fileList);

        String tags = dailyArticleForm.getTags();
        if (tags != null && !tags.isEmpty()) {
            List<String> tagNames = Arrays.asList(tags.split(","));
            for (String tagName : tagNames) {
                tagName = tagName.trim();
                TagDto tagDto = TagDto.builder()
                        .tagName(tagName)
                        .build();
                dailyArticleService.getTag(tagDto);

                TagArticleDto tagArticleDto = TagArticleDto.builder()
                        .dailyArticleId(dailyArticleForm.getDailyArticleId())
                        .build();
                dailyArticleService.getTagArticle(tagArticleDto);
            }
        }

        log.info("수신한 게시글 정보 : {}", dailyArticleDto);
        redirectAttributes.addFlashAttribute("createDailyArticleDto", createDailyArticleDto);
        redirectAttributes.addFlashAttribute("fileList", fileList);
        return "redirect:/daily/{categoryId}";
    }

    // 일상 게시글 목록 처리
    @GetMapping("{categoryId}")
    public String dailyList(@PathVariable("categoryId") int categoryId,
                            @ModelAttribute ParameterForm parameterForm,
//                            @RequestParam(value = "tagName", required = false) String tagName,
//                            @RequestParam(value = "requestPage", required = false) int requestPage,
//                            @RequestParam(value = "rowCount", required = false) int rowCount,

                            Model model) {
        log.info("전달된 파라메터 정보 : {}", parameterForm);

        SearchDto searchDto = SearchDto.builder()
                .limit(parameterForm.getElementSize())
                .page(parameterForm.getRequestPage())
                .tagName(parameterForm.getTagName())
                .build();


        List<DailyArticleDto> dailyArticleList = null;

        if (parameterForm.getTagName() != null && !parameterForm.getTagName().isEmpty()) {
            dailyArticleList = dailyArticleService.getDailyArticlesByTagName(categoryId, parameterForm.getTagName(), searchDto);
        } else {
            dailyArticleList = dailyArticleService.getDailyArticles(categoryId, searchDto);
        }

        // 페이징 처리를 위한 테이블 행의 개수 조회
        int selectedRowCount = dailyArticleService.getDailyArticleCount(categoryId, searchDto);
        log.info("조회된 행의 수: {} ", selectedRowCount);
        parameterForm.setRowCount(selectedRowCount);

        Pagination pagination = new Pagination(parameterForm);

        model.addAttribute("categoryId", categoryId);
        model.addAttribute("dailyArticleList", dailyArticleList);
        model.addAttribute("parameterForm", parameterForm);
        model.addAttribute("pagination", pagination);
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
        List<FileDto> fileList = dailyArticleService.getFiles(dailyArticleId);
        List<ReplyDto> replyList = dailyArticleService.getReplyList(dailyArticleId);
        int replyCount = dailyArticleService.getReplyCount(dailyArticleId);

        model.addAttribute("dailyArticle", dailyArticleDto);
        model.addAttribute("fileList", fileList);
        model.addAttribute("replyList", replyList);
        model.addAttribute("heartCount", heartCount);
        model.addAttribute("replyCount", replyCount);

        if (loginMember != null) {
            model.addAttribute("loginMember", loginMember);
        }

        log.info("수신한 댓글 목록 : {}", replyList);
        return "/daily/dailyRead";
    }

    // 댓글 등록 처리
//    @PostMapping("{categoryId}/read/{dailyArticleId}")
//    public String dailyReadReply(@PathVariable("categoryId") int categoryId, @ModelAttribute ReplyDto replyDto, @PathVariable("dailyArticleId") int dailyArticleId, Model model, HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
//
//        DailyArticleDto dailyArticleDto = dailyArticleService.getDailyArticle(categoryId, dailyArticleId);
//        List<FileDto> fileList = dailyArticleService.getFiles(dailyArticleId);
//        model.addAttribute("dailyArticle", dailyArticleDto);
//        model.addAttribute("fileList", fileList);
//        replyDto.setWriter(loginMember.getMemberId());
//        log.info("수신한 댓글 : {}", replyDto);
//        dailyArticleService.writeReply(replyDto);
//        return "redirect:/daily/{categoryId}/read/{dailyArticleId}";
//    }

    // 댓글 등록 클라이언트 사이드 렌더링
    @PostMapping("{categoryId}/read/{dailyArticleId}")
    public ResponseEntity<ReplyDto> registerReply(@PathVariable("categoryId") int categoryId,
                                                  @PathVariable("dailyArticleId") int dailyArticleId,
                                                  @RequestBody ReplyDto replyDto) {
        replyDto.setDailyArticleId(dailyArticleId);
        dailyArticleService.writeReply(replyDto);

        return ResponseEntity.ok(replyDto);
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

    // 게시글 삭제 처리
    @PostMapping("{categoryId}/delete/{dailyArticleId}")
    public String dailyDelete(@PathVariable("categoryId") int categoryId,
                              @PathVariable("dailyArticleId") int dailyArticleId
    ) {

        dailyArticleService.removeDailyArticle(categoryId, dailyArticleId);


        return "redirect:/daily/{categoryId}";
    }

    // 게시글 수정 폼으로 이동
    @GetMapping("{categoryId}/edit/{dailyArticleId}")
    public String dailyEditForm(@PathVariable("categoryId") int categoryId,
                                @PathVariable("dailyArticleId") int dailyArticleId,
                                Model model) {
        DailyArticleDto dailyArticleDto = dailyArticleService.getDailyArticle(categoryId, dailyArticleId);
        model.addAttribute("dailyArticleDto", dailyArticleDto);

        return "/daily/dailyEdit";
    }

    // 게시글 수정 처리
    @PostMapping("{categoryId}/edit/{dailyArticleId}")
    public String dailyEditAction(@PathVariable("categoryId") int categoryId,
                                  @PathVariable("dailyArticleId") int dailyArticleId,
                                  @ModelAttribute DailyArticleForm dailyArticleForm
    ) {

        DailyArticleDto editedDailyArticle = DailyArticleDto.builder()
                .title(dailyArticleForm.getTitle())
                .content(dailyArticleForm.getContent())
                .build();

        dailyArticleService.editDailyArticle(dailyArticleId, editedDailyArticle);

        return "redirect:/daily/{categoryId}";
    }

    // 댓글 목록 api
    @GetMapping("/getreplylist/{dailyArticleId}")
    public ResponseEntity<List<ReplyDto>> getReplyList(
            @PathVariable("dailyArticleId") int dailyArticleId) {
        List<ReplyDto> replyList = dailyArticleService.getReplyList(dailyArticleId);

        return ResponseEntity.ok(replyList);
    }

    // 댓글 삭제
//    @PostMapping("{categoryId}/delete-reply/{dailyArticleId}/{replyId}")
//    public String dailyReplyDelete(@PathVariable("categoryId") int categoryId,
//                                   @PathVariable("dailyArticleId") int dailyArticleId,
//                                   @PathVariable("replyId") int replyId) {
//
//        dailyArticleService.removeReply(dailyArticleId, replyId);
//
//        return "redirect:/daily/{categoryId}/read/{dailyArticleId}";
//    }

    @PostMapping("/delete-reply")
    public ResponseEntity<Void> removeReply(@RequestBody ReplyDto replyDto) {
        dailyArticleService.removeReply(replyDto.getDailyArticleId(), replyDto.getReplyId());

        return ResponseEntity.ok().build();
    }

    // 댓글 수 반환 api
    @GetMapping("reply-count/{dailyArticleId}")
    public ResponseEntity<Integer> replyCount (@PathVariable("dailyArticleId") int dailyArticleId) {
        int replyCount = dailyArticleService.getReplyCount(dailyArticleId);
        return ResponseEntity.ok(replyCount);
    }

    // 댓글 수정 api
    @PostMapping("/edit-reply")
    public ResponseEntity<Void> dailyEditReply(@RequestBody ReplyDto replyDto) {

        dailyArticleService.editReply(replyDto.getDailyArticleId(), replyDto.getReplyId(), replyDto.getContent());

        return ResponseEntity.ok().build();
    }
}
