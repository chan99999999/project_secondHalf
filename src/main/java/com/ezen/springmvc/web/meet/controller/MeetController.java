package com.ezen.springmvc.web.meet.controller;

import com.ezen.springmvc.domain.category.dto.CategoryDto;
import com.ezen.springmvc.domain.common.dto.SearchDto;
import com.ezen.springmvc.domain.dailyarticle.dto.ReplyDto;
import com.ezen.springmvc.domain.meetArticle.dto.*;
import com.ezen.springmvc.domain.category.service.CategoryService;
import com.ezen.springmvc.domain.meetArticle.service.MeetArticleService;
import com.ezen.springmvc.domain.member.dto.MemberDto;
import com.ezen.springmvc.domain.member.service.MemberService;
import com.ezen.springmvc.web.common.page.Pagination;
import com.ezen.springmvc.web.common.page.ParameterForm;
import com.ezen.springmvc.web.meet.form.MeetArticleForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/meet")
@Slf4j
@RequiredArgsConstructor
public class MeetController {

    private final MeetArticleService meetArticleService;
    private final CategoryService categoryService;
    private final MemberService memberService;

    @GetMapping("{categoryId}/register")
    public String meetRegister(@PathVariable("categoryId") int categoryId, Model model) {
        model.addAttribute("categoryId", categoryId);
        return "/meet/meetRegister";
    }

    //    목록
    @GetMapping("{categoryId}")
    public String meetList(@PathVariable("categoryId") int categoryId,
                           @ModelAttribute ParameterForm parameterForm,
                           Model model) {
        SearchDto searchDto = SearchDto.builder()
                .limit(parameterForm.getElementSize())
                .page(parameterForm.getRequestPage())
                .tagName(parameterForm.getTagName())
                .meetArticleId(parameterForm.getMeetArticleId())
                .title(parameterForm.getTitle())
                .build();
        List<MeetArticleDto> meetArticleList;
        // 태그 검색일 경우
        if (parameterForm.getTagName() != null && !parameterForm.getTagName().isEmpty()) {
            meetArticleList = new ArrayList<>();
            List<MeetTagDto> tagList = meetArticleService.findByAllMeetTagName(parameterForm.getTagName());
            if (!tagList.isEmpty()) {
                tagList.forEach(tagDto -> {
                    MeetArticleDto meetArticleDto = MeetArticleDto.builder()
                            .meetArticleId(tagDto.getMeetArticleId())
                            .title(tagDto.getTitle())
                            .content(tagDto.getContent())
                            .regdate(tagDto.getRegdate())
                            .time(tagDto.getTime())
                            .tagName(parameterForm.getTagName())
                            .build();
                    meetArticleList.add(meetArticleDto);
                });
            }
        } else if (parameterForm.getTitle() != null) { // 제목 검색일 경우..
            meetArticleList = meetArticleService.findByMeetTitle(categoryId, searchDto);
        } else {
            meetArticleList = meetArticleService.findByAllMeetArticle(categoryId);
        }

        // 각 게시글에 모임 참여자 수 조회 및 모델에 추가
        Map<Integer, Integer> meetRoomCounts = new HashMap<>();
        for (MeetArticleDto meetArticle : meetArticleList) {
            List<MeetRoomDto> joinMembers = meetArticleService.searchByMeetRoomId(meetArticle.getMeetArticleId());
            meetRoomCounts.put(meetArticle.getMeetArticleId(), joinMembers.size());
        }

        MeetTagDto tagList = MeetTagDto.builder()
                .tagName(parameterForm.getTagName())
                .build();
        for (MeetArticleDto meetArticleDto : meetArticleList) {
            parameterForm.getTagName();
            log.info("수신한 게시글 목록 : {}", meetArticleDto);
        }
        // 페이징 처리를 위한 테이블 행의 개수 조회
        int selectedRowCount = meetArticleService.findByMeetArticleCount(categoryId, searchDto);
        log.info("조회된 행의 수: {} ", selectedRowCount);
        parameterForm.setRowCount(selectedRowCount);
        Pagination pagination = new Pagination(parameterForm);
        log.info("조회된 페이지네이션 : {}", pagination);
        log.info("조회된 파라미터폼 : {}", parameterForm);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("meetArticleList", meetArticleList);
        model.addAttribute("parameterForm", parameterForm);
        model.addAttribute("pagination", pagination);
        model.addAttribute("tagList", tagList);
        model.addAttribute("meetRoomCounts", meetRoomCounts);
        return "/meet/meetList";
    }

    //    등록
    @PostMapping("{categoryId}/register")
    public String meetRegister(@PathVariable("categoryId") int categoryId,
                               @ModelAttribute MeetArticleForm meetArticleForm,
                               HttpServletRequest request) {
        log.info("수신한 정보들 : {}", meetArticleForm);
        HttpSession session = request.getSession();
        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
        MeetArticleDto meetArticleDto = MeetArticleDto.builder()
                .categoryId(categoryId)
                .memberId(loginMember.getMemberId())
                .placeId(meetArticleForm.getPlaceId()) // 추후 동적 변경 필요
                .title(meetArticleForm.getTitle())
                .content(meetArticleForm.getContent())
                .enter(meetArticleForm.getEnter())
                .tags(meetArticleForm.getTags())
                .build();
        log.info("모임등록 게시글 : {}", meetArticleDto.toString());
        meetArticleService.addMeet(meetArticleDto);
        return "redirect:/meet/{categoryId}";
    }

    //    게시글 상세보기
    @GetMapping("/read/{meetArticleId}")
    public String meetRead(@PathVariable("meetArticleId") int meetArticleId,
                           HttpServletRequest request,
                           Model model) {
        log.info("게시글 번호 : {}", meetArticleId);
        HttpSession session = request.getSession();
        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
        MeetArticleDto meetArticleDto = meetArticleService.readMeetArticle(3, meetArticleId);


        MemberDto writer = memberService.getMember(meetArticleDto.getMemberId());

        meetArticleDto.setNickname(writer.getNickname());
        meetArticleDto.setProfile(writer.getStorePicture());

        log.info("게시글 상세내용 : {}", meetArticleDto.toString());

        meetArticleService.meetHitcount(meetArticleDto);
        List<MeetReplyDto> meetReplyList = meetArticleService.meetReplyList(meetArticleId);
        int meetReplyCount = meetArticleService.meetReplyCount(meetArticleId);

        for (MeetReplyDto meetReplyDto : meetReplyList) {
            MemberDto commenter = memberService.getMember(meetReplyDto.getWriter());
            meetReplyDto.setProfile(commenter.getStorePicture());
            meetReplyDto.setNickname(commenter.getNickname());
        }

        List<MeetRoomDto> joinMembers = meetArticleService.searchByMeetRoomId(meetArticleId);
        List<String> joinMemberIds = new ArrayList<>();
        for (MeetRoomDto meetRoomDto : joinMembers) {
            MemberDto member = memberService.getMember(meetRoomDto.getJoinMemberId());
            meetRoomDto.setNickname(member.getNickname());
            joinMemberIds.add(meetRoomDto.getNickname());
        }

        model.addAttribute("joinMembers", joinMemberIds);

        model.addAttribute("meetArticleDto", meetArticleDto);
        model.addAttribute("meetReplyList", meetReplyList);
        model.addAttribute("meetReplyCount", meetReplyCount);
        if (loginMember != null) {
            model.addAttribute("loginMember", loginMember);
        }
        log.info("수신한 댓글 목록 : {}", meetReplyList);
        return "/meet/meetRead";
    }

    //    댓글 등록
    @PostMapping("/read/{meetArticleId}")
    public String meetCreateMeetReply(@ModelAttribute MeetReplyDto meetReplyDto,
                                  @PathVariable("meetArticleId") int meetArticleId,
                                  Model model,
                                  HttpServletRequest request) {
        HttpSession session = request.getSession();
        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
        MeetArticleDto meetArticleDto = meetArticleService.readMeetArticle(3, meetArticleId);
        model.addAttribute("meetArticleDto", meetArticleDto);
        meetReplyDto.setWriter(loginMember.getMemberId());
        log.info("수신한 댓글 : {}", meetReplyDto);
        meetArticleService.createMeetReply(meetReplyDto);
        return "redirect:/meet/read/{meetArticleId}";
    }

    @GetMapping("/join/{meetArticleId}")
    public String joinMeetAction(@PathVariable("meetArticleId") int meetArticleId, HttpSession session, Model model){

        MemberDto memberDto = (MemberDto) session.getAttribute("loginMember");
        MeetRoomDto meetRoomDto = MeetRoomDto.builder()
                .meetRoomId(meetArticleId)
                .joinMemberId(memberDto.getMemberId())
                .build();
        meetArticleService.enterMeetRoom(meetRoomDto);

        return "redirect:/meet/read/{meetArticleId}";
    }

    @GetMapping("/cancelJoin/{meetArticleId}")
    public String cancelJoinAction(@PathVariable("meetArticleId") int meetArticleId, HttpSession session, Model model){

        MemberDto memberDto = (MemberDto) session.getAttribute("loginMember");
        meetArticleService.deleteMeetRoom(meetArticleId, memberDto.getMemberId());
        return "redirect:/meet/read/{meetArticleId}";
    }

//    // 참여하기
//    @PostMapping("/{categoryId}/participate/{meetArticleId}")
//    @ResponseBody
//    public ResponseEntity<?> participate(@PathVariable int categoryId, @PathVariable int meetArticleId, @RequestBody String memberId) {
//        meetArticleService.participate(categoryId, meetArticleId, memberId);
//        return ResponseEntity.ok().build();
//    }
//
//    // 참여 취소
//    @PostMapping("/{categoryId}/cancelParticipation/{meetArticleId}")
//    @ResponseBody
//    public ResponseEntity<?> cancelParticipation(@PathVariable int categoryId, @PathVariable int meetArticleId, @RequestBody String memberId) {
//        meetArticleService.cancelParticipation(categoryId, meetArticleId, memberId);
//        return ResponseEntity.ok().build();
//    }

    @GetMapping("/getLoginMember")
    public ResponseEntity<MemberDto> getLoginMember(HttpServletRequest request) {
        HttpSession session = request.getSession();
        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
        log.info("전달받은 로그인 회원 : {}", loginMember);
        return ResponseEntity.ok(loginMember);
    }

    @GetMapping("/category")
    public String getCategory(Model model) {
        List<CategoryDto> categoryList = categoryService.getCategoryList();
        log.info("수신받은 카테고리 목록 : {}", categoryList);
        model.addAttribute("categoryList", categoryList);
        return "layout/template";
    }
}
