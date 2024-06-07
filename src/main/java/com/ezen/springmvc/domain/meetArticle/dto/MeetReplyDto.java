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
    private int replyCount;
    private String writer;
    private String content;
    private String nickname;
    private String profile;
}
