package com.ezen.springmvc.web.map.controller;

import com.ezen.springmvc.domain.review.dto.ReviewDto;
import com.ezen.springmvc.domain.review.service.ReviewService;
import com.ezen.springmvc.web.map.form.ReviewForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;// 데이터베이스에서 신규 장소 정보를 등록(더미)

@Controller
@RequestMapping("/map/place/review")
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/add")
    @ResponseBody
    public ReviewForm reviewMessage(@ModelAttribute("reviewForm") ReviewForm reviewForm) {

        //        reviewForm 임시데이터 설정
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


        ReviewDto reviewDto = ReviewDto.builder()
                .review(reviewForm.getReview())
                .placeId(reviewForm.getPlaceId())
                .memberId(reviewForm.getMemberId())
                .build();


        reviewService.addNewReview(reviewDto);
        return reviewForm;
    }


//    private final ReviewService reviewService;
//
//    // 리뷰 쓰기 화면 요청 처리
//    @RequestMapping("/write")
//    public String writeReviewForm(Model model, HttpServletRequest request) {
//        // 리뷰 폼 객체 생성
//        ReviewForm reviewForm = ReviewForm.builder().build();
//
//        // 현재 세션을 가져옴 (있을 경우)
//        HttpSession session = request.getSession(false);
//
//
//        if (session != null) {
//            // 세션에서 로그인된 멤버 정보를 가져옴
//            MemberDto memberDto = (MemberDto) session.getAttribute("loginMember");
//            log.info("session : {}", session);
//            if (memberDto != null) {
//                // 리뷰 폼에 멤버 ID를 설정
//                reviewForm.setMemberId(memberDto.getMemberId());
//                log.info("리뷰 폼 : {}", reviewForm.toString());
//            }
//        }
//
//
//        // 모델에 리뷰 폼을 추가하여 뷰에 전달
//        model.addAttribute("reviewForm", reviewForm);
//        log.info("리뷰 폼 : {}", reviewForm.toString());
//        // 리뷰 작성 폼 뷰 반환
//        return "/map/form/ReviewForm";
//    }
//
//    // 리뷰 쓰기 요청 처리 (Bean Validation 사용)
//    @PostMapping("/write")
//    public String reviewWriteAction(
//            @Validated @ModelAttribute ReviewForm reviewForm, // 검증된 리뷰 폼 데이터
//            BindingResult bindingResult, // 폼 데이터의 바인딩 및 검증 결과
//            RedirectAttributes redirectAttributes, // 리다이렉트 시 플래시 속성 설정
//            Model model) { // 모델 객체
//
//        // 폼 데이터에 오류가 있을 경우 다시 리뷰 작성 폼 뷰 반환
//        if (bindingResult.hasErrors()) {
//            return "/map/form/ReviewForm";
//        }
//
//        // 리뷰 Dto 객체 생성 및 설정
//        ReviewDto reviewDto = ReviewDto.builder()
//                .review(reviewForm.getReview())
//                .placeId(reviewForm.getPlaceId())
//                .memberId(reviewForm.getMemberId())
//                .build();
//
//        // 리뷰 서비스 호출하여 리뷰 저장
////        reviewService.writeReview(reviewDto);
//        log.info("리뷰 저장 : {}", reviewDto.toString());
//
//        // 리다이렉트시 플래시 속성 사용 예제 (현재 주석 처리됨)
////      redirectAttributes.addFlashAttribute("memberDto", memberDto);
//
//        // 리뷰 작성 후 홈 화면으로 리다이렉트
//        return "redirect:/";
//    }
}