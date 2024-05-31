package com.ezen.springmvc;

import com.ezen.springmvc.domain.review.dto.ReviewDto;
import com.ezen.springmvc.domain.review.mapper.ReviewMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class ReviewMapperTest {

    @Autowired
    private ReviewMapper reviewMapper;

    @Test
    @DisplayName("신규 리뷰 등록")
    @Disabled
    public void createTest() {
        ReviewDto reviewDto = ReviewDto.builder()
                .memberId("monday")
                .review("리뷰등록")
                .placeId(12273700L)
                .build();
        reviewMapper.createReview(reviewDto);
        log.info("신규 리뷰 등록 : {}", reviewDto);
    }
}
