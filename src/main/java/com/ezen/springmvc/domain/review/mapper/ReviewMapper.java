package com.ezen.springmvc.domain.review.mapper;

import com.ezen.springmvc.domain.member.dto.MemberDto;
import com.ezen.springmvc.domain.placemap.dto.MapDto;
import com.ezen.springmvc.domain.review.dto.ReviewDto;
import com.ezen.springmvc.web.map.form.ReviewListForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReviewMapper {

    /** 신규 리뷰 생성 */
    public void createReview(ReviewDto reviewDto);

    /** 전체 리뷰 조회 */
//    List<ReviewListForm> getAllReview();


    /** 특정 장소의 리뷰 조회 */
    List<ReviewListForm> getReviewsByPlaceId(@Param("placeId") Long placeId);
}