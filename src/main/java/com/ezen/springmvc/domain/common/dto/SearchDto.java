package com.ezen.springmvc.domain.common.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SearchDto {
    private String title;
    private String content;
    private String tagName;
    private String time;
    private String memberId;
    private String[] tags;
    private int meetArticleId;
    private int enter;
    private int categoryId;
    private int placeId;
    private int hitcount;
    private int limit;
    private int page;
}
