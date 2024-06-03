package com.ezen.springmvc;

import com.ezen.springmvc.domain.review.dto.ReviewDto;
import com.ezen.springmvc.domain.review.mapper.ReviewMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
public class ReviewMapperTest {

    @Autowired
    private ReviewMapper reviewMapper;
    private static final Logger log = LoggerFactory.getLogger(ReviewMapperTest.class);

//    @Test
//    @DisplayName("신규 리뷰 등록")
////    @Disabled
//    public void createTest() {
//        ReviewDto reviewDto = ReviewDto.builder()
//                .memberId("monday")
//                .review("리뷰등록2")
//                .placeId(12273700L)
//                .regdate(new java.sql.Timestamp(System.currentTimeMillis()))
//                .build();
//        reviewMapper.createReview(reviewDto);
//        log.info("신규 리뷰 등록 : {}", reviewDto);
//    }


    @Test
    @DisplayName("전체 리뷰 조회")
    public void findByAllReviewTest() {
        List<ReviewDto> reviews = reviewMapper.findByAllReview();
        assertNotNull(reviews, "리뷰 목록이 null이면 안 됩니다.");
        assertFalse(reviews.isEmpty(), "리뷰 목록이 비어 있으면 안 됩니다.");

        for (ReviewDto review : reviews) {
            log.info("전체 리뷰 : {}", review);
        }
    }
}
