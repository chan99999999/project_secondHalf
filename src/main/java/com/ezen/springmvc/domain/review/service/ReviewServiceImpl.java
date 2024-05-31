package com.ezen.springmvc.domain.review.service;
import com.ezen.springmvc.domain.review.dto.ReviewDto;
import com.ezen.springmvc.domain.review.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
/** ReviewService 구현체 */
public class ReviewServiceImpl implements ReviewService {

    private final ReviewMapper reviewMapper;

    @Override
    public void addNewReview(ReviewDto reviewDto){
        reviewMapper.createReview(reviewDto);
    }


}