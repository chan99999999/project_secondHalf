package com.ezen.springmvc.domain.placemap.mapper;

import com.ezen.springmvc.domain.placemap.dto.MapDto;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MapMapper {
    // 새로운 리뷰 장소 등록
    void createPlaceMap(MapDto mapDto);
    // placeId로 장소 상세 조회
    MapDto findByPlaceId(Long PlaceId);

    void updatePlaceMap(MapDto mapDto);
}