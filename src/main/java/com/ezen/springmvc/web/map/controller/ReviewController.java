package com.ezen.springmvc.web.map.controller;

import com.ezen.springmvc.domain.review.dto.ReviewDto;
import com.ezen.springmvc.domain.review.service.ReviewService;
import com.ezen.springmvc.web.map.form.ReviewForm;
import com.ezen.springmvc.web.map.form.ReviewListForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.List;

@Controller
@RequestMapping("/map/place/review")
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    @PostMapping("/add")
    @ResponseBody
    public ReviewForm reviewMessage(@RequestBody ReviewForm reviewForm) {
        log.info("Received review: {}", reviewForm);

        ReviewDto reviewDto = ReviewDto.builder()
                .review(reviewForm.getReview())
                .placeId(reviewForm.getPlaceId())
                .memberId(reviewForm.getMemberId())
                .build();

        reviewService.addNewReview(reviewDto);
        return reviewForm;
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
        List<ReviewListForm> reviews = reviewService.getReviewsByPlaceId(placeId);

        // 모델에 후기 목록을 추가합니다.
        model.addAttribute("reviews", reviews);
        log.info("Reviews: {}", reviews);

        // 나머지 코드는 그대로 유지합니다.
        return "redirect:/map/place";
    }




}