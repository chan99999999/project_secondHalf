package com.ezen.springmvc.domain.dailyarticle.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
/** 태그 DTO */
public class TagDto {
    private int tagId;      /** 태그 번호 */
    private String tagName; /** 태그 이름 */
}
