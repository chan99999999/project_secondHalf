package com.ezen.springmvc.domain.meetArticle.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MeetTagDto {
    private int tagId;
    private int tArticleId;
    private int meetArticleId;
    private int enter;
    private String title;
    private String content;
    private String tagName;
    private String regdate;
    private String time;
}
