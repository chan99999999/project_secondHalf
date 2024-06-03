package com.ezen.springmvc.web.map.controller;
import com.ezen.springmvc.domain.placemap.dto.MapDto;
import com.ezen.springmvc.domain.placemap.service.MapService;
import com.ezen.springmvc.web.map.form.ReviewForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/map")
@Slf4j
public class MapController {

    private final MapService mapService;

    // 생성자를 통해 MapService를 주입받습니다.
    @Autowired
    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    // 특정 장소의 정보를 JSON 형식으로 반환합니다.
    @GetMapping("/place")
    @ResponseBody
    public MapDto getPlaceInfo(@RequestParam("id") Long mapId) {
        MapDto mapDto = mapService.getPlaceInfoById(mapId);
        log.info("Fetched MapDto: {}", mapDto);
        return mapDto;
    }
    // GET 요청을 처리하여 맵 검색 폼을 반환합니다.
    @GetMapping
    public String searchMap() {
        return "/map/searchForm";
    }

    // 특정 장소의 정보를 JSON 형식으로 반환합니다.
    @GetMapping("/place/json")
    @ResponseBody
    public MapDto getPlaceInfoJson(@RequestParam("id") Long mapId) {
        MapDto mapDto = mapService.getPlaceInfoById(mapId);
        log.info("Fetched MapDto: {}", mapDto);
        return mapDto;
    }




    // POST 요청을 처리하여 장소 정보를 모델에 추가하고 뷰를 반환합니다.
    @PostMapping("/place")
    public String getPlaceInfo(
            @RequestParam("id") Long mapId,
            @RequestParam("place_name") String placeName,
            @RequestParam("address_name") String addressName,
            @RequestParam("category_name") String categoryName,
            @RequestParam("phone") String phone,
            @RequestParam("place_url") String placeUrl,
            @RequestParam("road_address_name") String roadAddressName,
            @RequestParam("x") String x,
            @RequestParam("y") String y,
            Model model) {

        // PlaceInfoDto 객체를 생성합니다.
        MapDto mapDto = MapDto.builder()
                .placeId(mapId)
                .mapId(mapId)
                .placeName(placeName)
                .addressName(addressName)
                .categoryName(categoryName)
                .phone(phone)
                .placeUrl(placeUrl)
                .roadAddressName(roadAddressName)
                .x(x)
                .y(y)
                .build();

        // 로그 추가: 생성된 MapDto 객체 출력
        log.info("생성된 Map Dto: {}", mapDto);

        // MapDto 객체를 처리합니다.
        MapDto processedPlaceInfo = mapService.processPlaceInfo(mapDto);

        // 로그 추가: 처리된 정보 출력
        log.info("처리된 map 정보: {}", processedPlaceInfo);

        // 처리된 정보를 모델에 추가합니다.
        model.addAttribute("mapDto", processedPlaceInfo);

        // 로그 추가: 모델에 정보가 잘 추가되었는지 확인
        log.info("모델에 추가된 mapDto: {}", model.containsAttribute("mapDto"));

        // MapDto 객체를 JSON 문자열로 변환하고 로그를 추가합니다.
        String json = mapService.processPlaceInfoToJson(processedPlaceInfo);

        // 로그 추가: JSON 문자열 출력
        log.info("JSON String 출력: {}", json);

        // 모델에 JSON 문자열을 추가
        model.addAttribute("jsonMapDto", json);
        log.info("모델에 추가된 jsonMapDto: {}", model.containsAttribute("jsonMapDto"));

        return "/map/place";
    }


    // 데이터베이스에서 place_id가 존재하는지 검색 (더미)
    @PostMapping("/place/info")
    @ResponseBody
    public ReviewForm findByPlaceId(@ModelAttribute("reviewForm") ReviewForm reviewForm) {
        reviewForm.setPlaceId(12273702L);
        mapService.findByPlaceId(reviewForm.getPlaceId());
        return reviewForm;
    }

//    // 데이터베이스에서 place_id가 존재하는지 검색 (동적으로)
//    @PostMapping("/place/info")
//    @ResponseBody
//    public ResponseEntity<MapDto> findByPlaceId(@RequestBody Map<String, Long> request) {
//        Long placeId = request.get("placeId");
//        MapDto mapDto = mapService.findByPlaceId(placeId);
//        if (mapDto != null) {
//            return ResponseEntity.ok(mapDto);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//    }


    // 데이터베이스에서 신규 장소 정보를 등록(더미)
    @PostMapping("/place/review")
    @ResponseBody
    public ReviewForm reviewPlace(@ModelAttribute("reviewForm") ReviewForm reviewForm) {
        // reviewForm 임시데이터 설정
        reviewForm.setPlaceId(12273702L);
        reviewForm.setMemberId("monday");
        reviewForm.setNickname("nickname");
        reviewForm.setReview("사용자 등록 리뷰 내용");
        reviewForm.setPlaceName("장소명");
        reviewForm.setAddressName("주소명");
        reviewForm.setRoadAddressName("도로명");
        reviewForm.setY("127.0628665469612");
        reviewForm.setX("37.5028534975179");
        log.info("리뷰 정보 : {}", reviewForm);

        MapDto mapDto = MapDto.builder()
                .placeId(reviewForm.getPlaceId())
                .mapId(reviewForm.getPlaceId())
                .addressName(reviewForm.getAddressName())
                .placeName(reviewForm.getPlaceName())
                .roadAddressName(reviewForm.getRoadAddressName())
                .x(reviewForm.getX())
                .y(reviewForm.getY())
                .build();
        mapService.addNewPlace(mapDto);
        return reviewForm;
    }

//    // 데이터베이스에서 신규 장소 정보를 등록 (동적으로)
//    @PostMapping("/place/review")
//    public ResponseEntity<String> reviewPlace(@ModelAttribute ReviewForm reviewForm) {
//        // 신규 장소 등록 코드
//        // 반환할 값이 없으므로 ResponseEntity<String> 사용
//        mapService.addNewPlace(reviewForm); // 폼 데이터를 직접 전달합니다.
//        return ResponseEntity.ok("신규 장소가 등록되었습니다.");
//    }

}