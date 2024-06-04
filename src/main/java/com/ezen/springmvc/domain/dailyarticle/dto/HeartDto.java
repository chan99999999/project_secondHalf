package com.ezen.springmvc.domain.dailyarticle.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
/** 좋아요 DTO */
public class HeartDto {
   private int heartId;          /** 좋아요 번호 */
   private int heartCount;       /** 좋아요 개수 */
   private int dailyArticleId;   /** 일상 게시글 번호 */
   private String memberId;      /** 좋아요 클릭한 아이디 */
}
