package com.ezen.springmvc.web.map.form;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
/* 신규 리뷰 쓰기 폼에 대응하는 Form 클래스 */
/* 여기서 전달받은 정보들을 ReviewDto에 등록 */
public class ReviewForm {
    private Long placeId;
    private String nickname;
    private String memberId;
    private String review;
    private String placeName;       // 장소명
    private String addressName;     // 지번주소
    private String roadAddressName; // 도로명주소
    private String phone;           // 전화번호
    private String y;               // y좌표(위도)
    private String x;               // x좌표(경도)
    private String categoryName;    // 카테고리 // DB에 저장X
    private String placeUrl;
}
