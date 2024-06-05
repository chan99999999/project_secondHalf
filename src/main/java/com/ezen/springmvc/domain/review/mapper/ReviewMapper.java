package com.ezen.springmvc.domain.review.mapper;
import com.ezen.springmvc.domain.review.dto.ReviewDto;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ReviewMapper {

    /** 신규 리뷰 생성 */
    public void createReview(ReviewDto reviewDto);
    /** 특정 장소의 리뷰 조회 */
    List<ReviewDto> getReviewsByPlaceId(long placeId);



}