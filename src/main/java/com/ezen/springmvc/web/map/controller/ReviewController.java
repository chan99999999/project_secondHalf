package com.ezen.springmvc.web.map.controller;

import com.ezen.springmvc.domain.member.dto.MemberDto;
import com.ezen.springmvc.domain.placemap.dto.MapDto;
import com.ezen.springmvc.domain.placemap.service.MapService;
import com.ezen.springmvc.domain.review.dto.ReviewDto;
import com.ezen.springmvc.domain.review.service.ReviewService;
import com.ezen.springmvc.web.map.form.ReviewForm;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/map/place/review")
@Slf4j
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final MapService mapService;

    @PostMapping("/add")
    @ResponseBody
    public ReviewForm reviewAddAction(@RequestBody ReviewForm reviewForm, HttpSession session) {
        log.info("reviewForm: {}", reviewForm);

        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");

        MapDto mapDto = MapDto.builder()
                .x(reviewForm.getX())
                .y(reviewForm.getY())
                .placeId(reviewForm.getPlaceId())
                .placeName(reviewForm.getPlaceName())
                .addressName(reviewForm.getAddressName())
                .mapId(reviewForm.getMapId())
                .roadAddressName(reviewForm.getRoadAddressName())
                .build();

        ReviewDto reviewDto = ReviewDto.builder()
                .review(reviewForm.getReview())
                .placeId(reviewForm.getPlaceId())
                .memberId(loginMember.getMemberId())
                .build();

        reviewForm.setNickname(loginMember.getNickname());
        reviewService.addNewReview(mapDto, reviewDto);
        return reviewForm;
    }

    @GetMapping("/list")
    public String getReviewsByPlaceId(@RequestParam("placeId") long placeId, Model model) {
        log.info("Received request for reviews of place with ID: {}", placeId);
        List<ReviewDto> reviews = reviewService.getReviewsByPlaceId(placeId);

        // 모델에 후기 목록을 추가합니다.
        model.addAttribute("reviewList", reviews);
        log.info("Reviews: {}", reviews);

        // 장소 정보를 모델에 추가합니다.
        MapDto mapDto = mapService.getPlaceInfoById(placeId);
        model.addAttribute("mapDto", mapDto);
        log.info("MapDto: {}", mapDto);

        // 모델에 리뷰를 추가하고 /map/place 뷰를 반환합니다.
        return "/map/place";
    }

}