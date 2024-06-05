package com.ezen.springmvc.domain.placemap.service;
import com.ezen.springmvc.domain.placemap.dto.MapDto;

public interface MapService {

    // MapDto를 그대로 반환합니다
    MapDto processPlaceInfo(MapDto mapDto);
    // (카카오맵)주어진 mapId를 사용하여 임의의 MapDto 객체를 생성하여 반환
    MapDto getPlaceInfoById(long mapId);
    // MapDto를 JSON 문자열로 변환
    String processPlaceInfoToJson(MapDto mapDto);
    // PlaceId를 사용하여 데이터베이스에서 장소정보 검색
    MapDto findByPlaceId(long placeId);
    // place_id 없을시 신규 등록하는 메서드
    void addNewPlace(MapDto mapDto);
}

