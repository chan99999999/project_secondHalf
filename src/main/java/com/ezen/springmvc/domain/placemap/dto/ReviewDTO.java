package com.ezen.springmvc.domain.placemap.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ReviewDTO {
        private String reviewId; //후기번호
        private String review;  // 후기내용
        private String placeId;  //장소번호
        private String regDate; // 등록일자
        private String memberId;  // 아이디
        private String nickname; // 닉네임
}
