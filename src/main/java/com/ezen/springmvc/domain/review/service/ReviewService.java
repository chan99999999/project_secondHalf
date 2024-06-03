package com.ezen.springmvc.domain.review.service;

import com.ezen.springmvc.domain.review.dto.ReviewDto;
import com.ezen.springmvc.web.map.form.ReviewListForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/** 리뷰 관련 비지니스 로직 처리 및 트랜잭션 관리 */
public interface ReviewService {

    // 신규 리뷰 등록
    void addNewReview(ReviewDto reviewDto);


//    List<ReviewListForm> getReviewsByPlaceId(Long placeId);
List<ReviewListForm> getReviewsByPlaceId(@Param("placeId") Long placeId);

}