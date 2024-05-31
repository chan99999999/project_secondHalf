package com.ezen.springmvc.domain.placemap.service;
import com.ezen.springmvc.domain.placemap.dto.MapDto;
import com.ezen.springmvc.domain.placemap.mapper.MapMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MapServiceImpl implements MapService {

    private final MapMapper mapMapper;

    // MapDto를 그대로 반환합니다
    @Override
    public MapDto processPlaceInfo(MapDto mapDTO) {
        return mapDTO;
    }

    // 입력으로 받은 MapDto 객체를 JSON 문자열로 변환
    @Override
    public String processPlaceInfoToJson(MapDto mapDTO) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(mapDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void addNewPlace(MapDto mapDto) {
        mapMapper.createPlaceMap(mapDto);
    }

    //
    // 주어진 mapId를 사용하여 임의의 MapDto 객체를 생성하여 반환
    @Override
    public MapDto getPlaceInfoById(Long mapId) {
        return MapDto.builder()

                .placeId(mapId)
                .mapId(mapId)
                .placeName("placeName")
                .addressName("addressName")
                .roadAddressName("roadAddressName")
                .x("x")
                .y("y")
                .build();
    }

    //데이터베이스에 place_id 에 해당하는 정보가 있는지 조회



    // 데이터베이스에 신규 place 등록
//    @Override
//    @Transactional
//    public void toMapEntity(MapDto mapDTO) {
//        // MapDto를 MapEntity로 변환하여 Mapper에 전달
//        MapEntity mapEntity = convertToMapEntity(mapDTO);
//        mapMapper.toMapEntity(mapEntity);
//    }
//
//    // MapDto를 MapEntity로 변환하는 메서드
//    private MapEntity convertToMapEntity(MapDto mapDTO) {
//        MapEntity mapEntity = new MapEntity();
//        mapEntity.setPlaceId(mapDTO.getPlaceId());
//        mapEntity.setMapId(mapDTO.getMapId());
//        mapEntity.setPlaceName(mapDTO.getPlaceName());
//        mapEntity.setAddressName(mapDTO.getAddressName());
//        mapEntity.setRoadAddressName(mapDTO.getRoadAddressName());
//        mapEntity.setX(mapDTO.getX());
//        mapEntity.setY(mapDTO.getY());
//        // 기타 필드 설정
//
//        return mapEntity;
//    }
//
//
//    // 데이터베이스에서 place_id가 존재하는지 검색해보는 메서드
//    @Override
//    public MapDto searchByPlaceId(Long placeId) {
//        return null;
//    }
//
//    // place_id 없을시 신규 등록하는 메서드
//    @Override
//    public MapDto addNewPlace(Long placeId) {
//        return null;
//    }

}