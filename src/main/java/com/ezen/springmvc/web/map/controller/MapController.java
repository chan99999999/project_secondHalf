package com.ezen.springmvc.web.map.controller;

import com.ezen.springmvc.domain.member.dto.MemberDto;
import com.ezen.springmvc.domain.member.service.MemberService;
import com.ezen.springmvc.domain.placemap.dto.MapDto;
import com.ezen.springmvc.domain.placemap.service.MapService;
import com.ezen.springmvc.domain.review.dto.ReviewDto;
import com.ezen.springmvc.domain.review.mapper.ReviewMapper;
import com.ezen.springmvc.domain.review.service.ReviewService;
import com.ezen.springmvc.web.map.form.ReviewForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/map")
@Slf4j
public class MapController {

    private final MapService mapService;
    private final ReviewService reviewService;
    private final MemberService memberService;

    // 특정 장소의 정보를 JSON 형식으로 반환합니다.
    @GetMapping("/place")
    @ResponseBody
    public MapDto getPlaceInfo(@RequestParam("id") long mapId) {
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
    public MapDto getPlaceInfoJson(@RequestParam("id") long mapId) {
        log.info("mapId : {}", mapId);
        MapDto mapDto = mapService.getPlaceInfoById(mapId);
        log.info("Fetched MapDto: {}", mapDto);
        return mapDto;
    }


    // POST 요청을 처리하여 장소 정보를 모델에 추가하고 뷰를 반환합니다.
    @PostMapping("/place")
    public String getPlaceInfo(
            @RequestParam("id") long mapId,
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

        // MapDto 객체를 처리합니다.
        MapDto processedPlaceInfo = mapService.processPlaceInfo(mapDto);


        List<ReviewDto> reviewList = reviewService.getReviewsByPlaceId(mapId);

        for (ReviewDto review : reviewList) {
            MemberDto memberDto = memberService.getMember(review.getMemberId());
            review.setProfile(memberDto.getStorePicture());
        }

        ReviewForm reviewForm = ReviewForm.builder().build();

        // 처리된 정보를 모델에 추가합니다.
        model.addAttribute("mapDto", processedPlaceInfo);
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("reviewForm", reviewForm);

        // MapDto 객체를 JSON 문자열로 변환하고 로그를 추가합니다.
        String json = mapService.processPlaceInfoToJson(processedPlaceInfo);

        // 모델에 JSON 문자열을 추가
        model.addAttribute("jsonMapDto", json);
        log.info("모델에 추가된 jsonMapDto: {}", model.containsAttribute("jsonMapDto"));

        return "/map/place";
    }


    // 데이터베이스에서 place_id가 존재하는지 검색 (동적)
    @PostMapping("/place/info")
    @ResponseBody
    public ReviewForm findByPlaceId(@RequestBody ReviewForm reviewForm) {
        log.info("Received request to find place by ID: {}", reviewForm.getPlaceId());
        MapDto mapDto = mapService.findByPlaceId(reviewForm.getPlaceId());
        if (mapDto != null) {
            reviewForm.setPlaceId(mapDto.getPlaceId());
            reviewForm.setPlaceName(mapDto.getPlaceName());
            reviewForm.setAddressName(mapDto.getAddressName());
            reviewForm.setRoadAddressName(mapDto.getRoadAddressName());
            reviewForm.setX(mapDto.getX());
            reviewForm.setY(mapDto.getY());
        }
        log.info("Fetched MapDto: {}", mapDto);
        return reviewForm;
    }

    @PostMapping("/place/review")
    @ResponseBody
    public ReviewForm reviewPlace(@RequestBody ReviewForm reviewForm) {
        log.info("Received review: {}", reviewForm);
        // 지도 정보
        MapDto mapDto = MapDto.builder()
                .placeId(reviewForm.getPlaceId())
                .mapId(reviewForm.getPlaceId())
                .addressName(reviewForm.getAddressName())
                .placeName(reviewForm.getPlaceName())
                .roadAddressName(reviewForm.getRoadAddressName())
                .x(reviewForm.getX())
                .y(reviewForm.getY())
                .build();

        // DB에 맵 정보가 없을 시, 추가 생성
        mapService.addNewPlace(mapDto);
        return reviewForm;
    }
}