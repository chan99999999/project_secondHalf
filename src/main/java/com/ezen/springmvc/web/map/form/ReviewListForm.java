package com.ezen.springmvc.web.map.form;

import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

/* 리뷰 목록 폼에 대응하는 Form 클래스 */
public class ReviewListForm {
    private int reviewId;
    private Long reviewPlaceId;
    private String writer; // 닉네임 추가
    private String writerId;
    private String content; // 리뷰 추가
    private Timestamp writeDate;
}