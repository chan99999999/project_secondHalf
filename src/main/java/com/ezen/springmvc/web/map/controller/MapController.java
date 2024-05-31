package com.ezen.springmvc.web.map.controller;
import com.ezen.springmvc.domain.placemap.dto.MapDto;
import com.ezen.springmvc.domain.placemap.service.MapService;
import com.ezen.springmvc.web.map.form.ReviewForm;
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

    // 생성자를 통해 MapService를 주입받습니다.
    @Autowired
    public MapController(MapService mapService) {
        this.mapService = mapService;
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
//        MapDto mapDTO = mapService.getPlaceInfoById(mapId);
//        log.info("Fetched MapDTO: {}", mapDTO);
//        return mapDTO;
        return null;
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

        // PlaceInfoDTO 객체를 생성합니다.
        MapDto mapDTO = MapDto.builder()
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

        // 로그 추가: 생성된 MapDTO 객체 출력
        log.info("생성된 Map DTO: {}", mapDTO);

        // MapDTO 객체를 처리합니다.
        MapDto processedPlaceInfo = mapService.processPlaceInfo(mapDTO);

        // 로그 추가: 처리된 정보 출력
        log.info("처리된 map 정보: {}", processedPlaceInfo);

        // 처리된 정보를 모델에 추가합니다.
        model.addAttribute("mapDTO", processedPlaceInfo);

        // 로그 추가: 모델에 정보가 잘 추가되었는지 확인
        log.info("모델에 추가된 mapDTO: {}", model.containsAttribute("mapDTO"));

        // MapDTO 객체를 JSON 문자열로 변환하고 로그를 추가합니다.
        String json = mapService.processPlaceInfoToJson(processedPlaceInfo);

        // 로그 추가: JSON 문자열 출력
        log.info("JSON String 출력: {}", json);

        // 모델에 JSON 문자열을 추가
        model.addAttribute("jsonMapDTO", json);
        log.info("모델에 추가된 jsonMapDTO: {}", model.containsAttribute("jsonMapDTO"));

        return "/map/place";
    }

    

    // 데이터베이스에서 place_id가 존재하는지 검색해보는 메서드
    @PostMapping("/place/review")
    @ResponseBody
    public ReviewForm reviewPlace(@ModelAttribute("reviewForm") ReviewForm reviewForm) {
//        reviewForm 임시데이터 설정
        reviewForm.setPlaceId(12273702L);
        reviewForm.setMemberId("monday");
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



}