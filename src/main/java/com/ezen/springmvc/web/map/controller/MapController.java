package com.ezen.springmvc.web.map.controller;

import com.ezen.springmvc.domain.placemap.dto.MapDTO;
import com.ezen.springmvc.domain.placemap.service.MapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/map")
@Slf4j
public class MapController {

    private final MapService mapService;

    @Autowired
    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping
    public String searchMap() {
        return "/map/searchForm";
    }

    @GetMapping("/place/json")
    @ResponseBody
    public MapDTO getPlaceInfoJson(@RequestParam("id") String mapId) {
        MapDTO mapDTO = mapService.getPlaceInfoById(mapId);
        log.info("Fetched MapDTO: {}", mapDTO);
        return mapDTO;
    }

    @PostMapping("/place")
    public String getPlaceInfo(
            @RequestParam("id") String mapId,
            @RequestParam("place_name") String placeName,
            @RequestParam("address_name") String addressName,
            @RequestParam("category_name") String categoryName,
            @RequestParam("phone") String phone,
            @RequestParam("place_url") String placeUrl,
            @RequestParam("road_address_name") String roadAddressName,
            @RequestParam("x") String x, // 경도 변수명 변경
            @RequestParam("y") String y, // 위도 변수명 변경
            Model model) {

        // PlaceInfoDTO 객체를 생성합니다.
        MapDTO mapDTO = MapDTO.builder()
                .mapId(mapId)
                .placeName(placeName)
                .addressName(addressName)
                .categoryName(categoryName)
                .phone(phone)
                .placeUrl(placeUrl)
                .roadAddressName(roadAddressName)
                .x(x) // 경도 변수명 변경
                .y(y) // 위도 변수명 변경
                .build();

        // 로그 추가: 생성된 MapDTO 객체 출력
        log.info("Map DTO: {}", mapDTO);

        // MapDTO 객체를 처리합니다.
        MapDTO processedPlaceInfo = mapService.processPlaceInfo(mapDTO);

        // 로그 추가: 처리된 정보 출력
        log.info("Processed Place Info: {}", processedPlaceInfo);

        // 처리된 정보를 모델에 추가합니다.
        model.addAttribute("mapDTO", processedPlaceInfo);

        // 로그 추가: 모델에 정보가 잘 추가되었는지 확인
        log.info("Model contains mapDTO: {}", model.containsAttribute("mapDTO"));

        // MapDTO 객체를 JSON 문자열로 변환하고 로그를 추가합니다.
        String json = mapService.processPlaceInfoToJson(processedPlaceInfo);

        // 로그 추가: JSON 문자열 출력
        log.info("JSON String: {}", json);

        // 모델에 JSON 문자열을 추가
        model.addAttribute("jsonMapDTO", json);

        return "/map/place";
    }


}
