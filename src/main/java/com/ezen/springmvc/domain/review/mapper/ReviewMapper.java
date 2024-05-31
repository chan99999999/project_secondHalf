package com.ezen.springmvc.domain.review.mapper;

import com.ezen.springmvc.domain.placemap.dto.MapDto;
import com.ezen.springmvc.domain.review.dto.ReviewDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewMapper {

    /** 신규 리뷰 생성 */
    public void createReview(ReviewDto reviewDto);
}