package com.ezen.springmvc.domain.review.dto;

import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ReviewDto {
        private int reviewId; //후기번호
        private String review;  // 후기내용
        private long placeId;  //장소번호
        private Timestamp regdate; // 등록일자
        private String memberId;  // 아이디
        private String nickname; // 닉네임
        private String profile;
}
