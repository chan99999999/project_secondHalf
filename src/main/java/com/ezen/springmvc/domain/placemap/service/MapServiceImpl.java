package com.ezen.springmvc.domain.placemap.service;
import com.ezen.springmvc.domain.placemap.dto.MapDto;
import com.ezen.springmvc.domain.placemap.mapper.MapMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MapServiceImpl implements MapService {

    private final MapMapper mapMapper;

    // MapDto를 그대로 반환합니다
    @Override
    public MapDto processPlaceInfo(MapDto mapDto) {
        return mapDto;
    }

    // 입력으로 받은 MapDto 객체를 JSON 문자열로 변환
    @Override
    public String processPlaceInfoToJson(MapDto mapDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(mapDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }




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


    // PlaceId를 사용하여 데이터베이스에서 장소정보 검색
    @Override
    public MapDto findByPlaceId(Long placeId) {
        mapMapper.findByPlaceId(placeId);

        return null;
    }

    // place_id 없을시 신규 등록하는 메서드
    @Override
    public void addNewPlace(MapDto mapDto) {
        mapMapper.createPlaceMap(mapDto);
    }






    //데이터베이스에 place_id 에 해당하는 정보가 있는지 조회

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