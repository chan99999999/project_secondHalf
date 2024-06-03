package com.ezen.springmvc.web.map.form;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

/* 리뷰 목록 폼에 대응하는 Form 클래스 */
@Data
public class ReviewListForm {
    private Long reviewId;
    private Long placeId;
    private String nickname; // 닉네임 추가
    private String memberId;
    private String review; // 리뷰 추가
    private String regdate;
}