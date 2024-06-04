package com.ezen.springmvc;

import com.ezen.springmvc.domain.review.dto.ReviewDto;
import com.ezen.springmvc.domain.review.mapper.ReviewMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
public class ReviewMapperTest {

    @Autowired
    private ReviewMapper reviewMapper;


//    @Test
//    @DisplayName("신규 리뷰 등록")
//    public void createTest() {
//        ReviewDto reviewDto = ReviewDto.builder()
//                .memberId("monday")
//                .review("리뷰2")
//                .placeId(12273700L)
//                .regdate(new java.sql.Timestamp(System.currentTimeMillis()))
//                .build();
//        reviewMapper.createReview(reviewDto);
//
//        log.info("신규 리뷰 등록 : {}", reviewDto);
//    }
//

//
//    @Test
//    @DisplayName("모든 리뷰 조회")
//    public void getAllReviewTest() {
//        List<ReviewListForm> reviews = reviewMapper.getAllReview();
//        assertNotNull(reviews, "리뷰 목록이 null이면 안 됩니다.");
//        assertFalse(reviews.isEmpty(), "리뷰 목록이 비어 있으면 안 됩니다.");
//
//        for (ReviewListForm review : reviews) {
//            log.info("리뷰: {}", review);
//        }
//    }

    @Test
    @DisplayName("특정 장소의 리뷰 목록 조회")
    public void getReviewsByPlaceIdTest() {
        Long placeId = 12273700L;
        List<ReviewDto> reviews = reviewMapper.getReviewsByPlaceId(placeId);
        assertNotNull(reviews, "리뷰 목록이 null이면 안 됩니다.");
        assertFalse(reviews.isEmpty(), "리뷰 목록이 비어 있으면 안 됩니다.");

        for (ReviewDto review : reviews) {
            log.info("장소 ID {}의 리뷰: {}", placeId, review);
        }
    }


}
