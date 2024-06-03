package com.ezen.springmvc.domain.review.service;
import com.ezen.springmvc.domain.review.dto.ReviewDto;
import com.ezen.springmvc.domain.review.mapper.ReviewMapper;
import com.ezen.springmvc.web.map.form.ReviewListForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
/** ReviewService 구현체 */
public class ReviewServiceImpl implements ReviewService {

    private final ReviewMapper reviewMapper;

    @Override
    public void addNewReview(ReviewDto reviewDto){
        reviewMapper.createReview(reviewDto);
    }

    @Override
    public List<ReviewListForm> getReviewsByPlaceId(Long placeId) {
        return reviewMapper.getReviewsByPlaceId(placeId);
    }


}