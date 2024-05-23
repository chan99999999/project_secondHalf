package com.ezen.springmvc.domain.meetArticle.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ReplyDto {
    private int replyId;
    private int meetArticleId;
    private String content;
    private String regdate;
}
