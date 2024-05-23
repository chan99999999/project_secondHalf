package com.ezen.springmvc.domain.meetArticle.dto;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MeetArticleDto {
    private int meetArticleId;
    private String title;
    private String content;
    private String regdate;
    private String time;
    private int enter;
    private int hitcount;
    private int categoryId;
    private String memberId;
    private int placeId;
}
