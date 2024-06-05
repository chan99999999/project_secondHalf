package com.ezen.springmvc.domain.dailyarticle.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

/** 태그 게시글(매핑) DTO */
public class TagArticleDto {
    private int dailyArticleId; /** 일상 게시글 번호 */
    private int tagId;          /** 태그 번호 */
}
