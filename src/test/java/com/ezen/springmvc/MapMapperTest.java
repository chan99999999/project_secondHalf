package com.ezen.springmvc;

import com.ezen.springmvc.domain.placemap.dto.MapDto;
import com.ezen.springmvc.domain.placemap.mapper.MapMapper;
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
public class MapMapperTest {

    @Autowired
    private MapMapper mapMapper;

    @Test
    @DisplayName("신규 리뷰 장소 등록")
    @Disabled
    public void createPlaceMapTest() {
        MapDto mapDto = MapDto.builder()
                .placeId(12273701L)
                .mapId(12273701L)
                .addressName("주소 이름")
                .placeName("테스트 장소명")
                .roadAddressName("테스트 도로명 주소")
                .x("127.0628665469612")
                .y("37.5028534975179")
                .build();
        mapMapper.createPlaceMap(mapDto);

        log.info("신규 리뷰 장소 등록 : {}", mapDto );
    }


    @Test
    @DisplayName("placeId로 장소검색")
    @Disabled
    public void findByPlaceIdTest() {
        MapDto mapDto = mapMapper.findByPlaceId(12273701L);
        log.info("placeId로 장소검색 : {}", mapDto );
    }

}
