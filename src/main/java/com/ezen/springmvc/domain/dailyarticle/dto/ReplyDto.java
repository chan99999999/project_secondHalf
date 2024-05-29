package com.ezen.springmvc.domain.dailyarticle.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ReplyDto {
    private int replyId;
    private int dailyArticleId;
    private int meetArticleId;
    private String writer;
    private String content;
    private String regdate;
    private int replyCount;
}
