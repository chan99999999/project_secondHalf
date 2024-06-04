package com.ezen.springmvc.web.map.controller;

import com.ezen.springmvc.domain.member.dto.MemberDto;
import com.ezen.springmvc.domain.placemap.dto.MapDto;
import com.ezen.springmvc.domain.placemap.service.MapService;
import com.ezen.springmvc.domain.review.dto.ReviewDto;
import com.ezen.springmvc.domain.review.dto.ReviewRequest;
import com.ezen.springmvc.domain.review.service.ReviewService;
import com.ezen.springmvc.web.map.form.ReviewForm;
import com.ezen.springmvc.web.map.form.ReviewListForm;
import jakarta.servlet.http.HttpSession;
import jdk.jfr.ContentType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String reviewMessage(@RequestParam("x") String x, @RequestParam("y") String y, @RequestParam("placeId") Long placeId,
                                @RequestParam("placeName") String placeName, @RequestParam("addressName") String addressName, @RequestParam("roadAddressName") String roadAddressName,
                                @RequestParam("content") String content, HttpSession session) {

        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
        MapDto mapDto = MapDto.builder()
                .x(x)
                .y(y)
                .placeId(placeId)
                .placeName(placeName)
                .addressName(addressName)
                .roadAddressName(roadAddressName)
                .build();

        mapService.addNewPlace(mapDto);

        log.info("댓글 내용: {}", mapDto.toString());

        ReviewDto reviewDto = ReviewDto.builder()
                .review(content)
                .placeId(mapDto.getPlaceId())
                .memberId(loginMember.getMemberId())
                .build();

        reviewService.addNewReview(reviewDto);
        return "redirect:/map/place/details?id=" + mapDto.getPlaceId();
    }


//    @GetMapping("/list")
//    @ResponseBody
//    public List<ReviewListForm> getReviewsByPlaceId(@RequestParam("placeId") Long placeId) {
//        log.info("Received request for reviews of place with ID: {}", placeId);
//        return reviewService.getReviewsByPlaceId(placeId);
//    }

    @GetMapping("/list")
    public String getReviewsByPlaceId(@RequestParam("placeId") Long placeId, Model model) {
        log.info("Received request for reviews of place with ID: {}", placeId);
        List<ReviewDto> reviews = reviewService.getReviewsByPlaceId(placeId);

        // 모델에 후기 목록을 추가합니다.
        model.addAttribute("reviews", reviews);
        log.info("Reviews: {}", reviews);

        // 나머지 코드는 그대로 유지합니다.
        return "redirect:/map/place";
    }


}