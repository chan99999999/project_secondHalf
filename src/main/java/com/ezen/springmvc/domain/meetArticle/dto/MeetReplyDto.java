package com.ezen.springmvc.domain.meetArticle.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MeetReplyDto {
    private int replyId;
    private int meetArticleId;
    private String writer;
    private String content;
    private String regdate;
    private int replyCount;
}
