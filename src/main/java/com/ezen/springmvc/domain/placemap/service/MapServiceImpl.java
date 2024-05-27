package com.ezen.springmvc.domain.placemap.service;

import com.ezen.springmvc.domain.placemap.dto.MapDTO;
import com.ezen.springmvc.domain.placemap.mapper.MapMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapServiceImpl implements MapService {

    private final MapMapper mapMapper;

    @Autowired
    public MapServiceImpl(MapMapper mapMapper) {
        this.mapMapper = mapMapper;
    }

    @Override
    public MapDTO processPlaceInfo(MapDTO mapDTO) {
        // placeInfoDTO 객체를 처리하는 로직을 작성합니다.
        // 여기서는 단순히 받은 정보를 반환합니다.
        return mapDTO;
    }

    @Override
    public String processPlaceInfoToJson(MapDTO mapDTO) {
        // Jackson ObjectMapper를 사용하여 MapDTO 객체를 JSON 문자열로 변환합니다.
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(mapDTO);
        } catch (JsonProcessingException e) {
            // JSON 변환 중 예외가 발생한 경우 처리합니다.
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public MapDTO getPlaceInfoById(String mapId) {
        // 여기에 mapId를 사용하여 MapDTO를 가져오는 로직을 작성합니다.
        // 데이터베이스나 다른 소스로부터 mapId에 해당하는 데이터를 가져오는 로직입니다.
        // 아래는 예시로, 실제 구현에서는 데이터베이스 조회 등을 할 수 있습니다.

        // 예시 데이터
        return MapDTO.builder()
                .mapId(mapId)
                .placeName("성민복지관")
                .addressName("서울 노원구 상계동 321-56")
                .roadAddressName("서울 노원구 노원로32길 30-3")
                .phone("02-931-7970")
                .x("127.06916926781204")
                .y("37.65571365636447")
                .categoryName("사회,공공기관 > 단체,협회 > 사회복지시설 > 장애인복지시설")
                .placeUrl("http://place.map.kakao.com/11728630")
                .build();
    }


    @Override
    public void savePlaceInfo(MapDTO mapDTO) {
        mapMapper.insertPlaceInfo(mapDTO);
    }

//    @Override
//    public MapDTO getPlaceInfoById(String mapId) {
//        return mapMapper.getPlaceInfoById(mapId);
//    }

    @Override
    public void updatePlaceInfo(MapDTO mapDTO) {
        mapMapper.updatePlaceInfo(mapDTO);
    }

    @Override
    public void deletePlaceInfo(String mapId) {
        mapMapper.deletePlaceInfo(mapId);
    }




}
