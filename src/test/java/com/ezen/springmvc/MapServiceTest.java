package com.ezen.springmvc;

import com.ezen.springmvc.domain.member.dto.MemberDto;
import com.ezen.springmvc.domain.member.service.MemberService;
import com.ezen.springmvc.domain.placemap.dto.MapDto;
import com.ezen.springmvc.domain.review.dto.ReviewDto;
import com.ezen.springmvc.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class MapServiceTest {

	@Autowired
	private ReviewService reviewService;

	@Test
	@DisplayName("맵 리뷰등록")
//	@Disabled
	void addReviewTest() {
		MapDto mapDto = MapDto.builder()
				.placeId(33)
				.mapId(33)
				.addressName("가나다")
				.placeName("테스트 장소명")
				.roadAddressName("테스트 도로명 주소")
				.x("127.0628665469612")
				.y("37.5028534975179")
				.build();


		ReviewDto reviewDto = ReviewDto.builder()
				.memberId("monday")
				.review("리뷰다 간다아")
				.placeId(33)
				.build();

		reviewService.addNewReview(mapDto, reviewDto);
	}

}







