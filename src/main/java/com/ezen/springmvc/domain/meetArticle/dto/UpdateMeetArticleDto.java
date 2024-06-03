package com.ezen.springmvc.domain.meetArticle.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UpdateMeetArticleDto {
    private int meetArticleId;
    private int categoryId;
    private String title;
    private String content;
}
