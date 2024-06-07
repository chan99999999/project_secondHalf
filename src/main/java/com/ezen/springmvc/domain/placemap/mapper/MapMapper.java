package com.ezen.springmvc.domain.placemap.mapper;

import com.ezen.springmvc.domain.placemap.dto.MapDto;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MapMapper {
    // 새로운 리뷰 장소 등록
    void createPlaceMap(MapDto mapDto);
    // placeId로 장소 상세 조회
    MapDto findByPlaceId(long PlaceId);
    // 장소 정보 업데이트
    void updatePlaceMap(MapDto mapDto);
}