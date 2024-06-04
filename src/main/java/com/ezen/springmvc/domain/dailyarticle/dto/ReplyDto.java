package com.ezen.springmvc.domain.dailyarticle.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
/** 댓글 DTO */
public class ReplyDto {
    private int replyId;        /** 댓글 번호 */
    private int dailyArticleId; /** 일상 게시글 번호 */
    private String writer;      /** 댓글 작성한 아이디 */
    private String content;     /** 댓글 내용 */
    private String regdate;     /** 댓글 작성 일자 */
}
