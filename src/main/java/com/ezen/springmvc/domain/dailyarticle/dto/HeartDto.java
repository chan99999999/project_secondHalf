package com.ezen.springmvc.domain.dailyarticle.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class HeartDto {
   private int heartId;
   private int heartCount;
   private int dailyArticleId;
   private String memberId;
}
