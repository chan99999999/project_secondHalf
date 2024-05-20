package com.ezen.springmvc.domain.dailyarticle.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class DailyArticleDto {
    private int dailyArticleId;
    private String title;
    private String content;
    private String regdate;
    private int hitCount;
    private int categoryId;
    private String memberId;
}
