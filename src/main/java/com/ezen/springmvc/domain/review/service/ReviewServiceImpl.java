package com.ezen.springmvc.domain.review.service;
import com.ezen.springmvc.domain.placemap.dto.MapDto;
import com.ezen.springmvc.domain.placemap.mapper.MapMapper;
import com.ezen.springmvc.domain.placemap.service.MapService;
import com.ezen.springmvc.domain.review.dto.ReviewDto;
import com.ezen.springmvc.domain.review.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
/** ReviewService 구현체 */
public class ReviewServiceImpl implements ReviewService {
    private final MapMapper mapMapper;
    private final ReviewMapper reviewMapper;
    private final MapService mapService;

    @Override
    @Transactional
    public void addNewReview(MapDto mapDto, ReviewDto reviewDto){
        // 지도에 신규 장소 등록
        mapService.addNewPlace(mapDto);
        // 신규 장소에 대한 리뷰 등록
        reviewMapper.createReview(reviewDto);
    }

//    @Override
//    public List<ReviewListForm> getReviewsByPlaceId(Long placeId) {
//        return reviewMapper.getReviewsByPlaceId(placeId);
//    }



    @Override
    public List<ReviewDto> getReviewsByPlaceId(long placeId) {
        return reviewMapper.getReviewsByPlaceId(placeId);
    }




}