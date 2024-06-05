package com.ezen.springmvc.domain.dailyarticle.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
/** 공지사항 게시글 DTO */
public class NoticeArticleDto {
    private int noticeArticleId;     /** 일상 게시글 번호 */
    private String title;           /** 일상 게시글 제목 */
    private String content;         /** 일상 게시글 내용 */
    private String regdate;         /** 일상 게시글 등록 일자 */
    private int hitCount;           /** 일상 게시글 조회수 */
    private int categoryId;         /** 일상 게시글 카테고리 번호 */
    private String memberId;        /** 일상 게시글 작성자 아이디 */
    private String encryptedName;   /** 일상 게시글 파일 이름 */
    private int totalHeartCount;    /** 일상 게시글 좋아요 총 개수 */
    private String tagNames;        /** 일상 게시글 태그 이름들 */
    private String nickName;        /** 일상 게시글 작성자 닉네임 */
}
