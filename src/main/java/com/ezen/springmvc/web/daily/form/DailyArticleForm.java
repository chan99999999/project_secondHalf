package com.ezen.springmvc.web.daily.form;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
/** 신규 일상 게시글 작성 폼에 대응하는 Form 클래스 */
public class DailyArticleForm {
    private int dailyArticleId;                 /** 일상 게시글 번호 */
    private String memberId;                    /** 일상 게시글 작성한 아이디 */
    private String regdate;                     /** 일상 게시글 작성 일자 */
    private String title;                       /** 일상 게시글 제목 */
    private String content;                     /** 일상 게시글 내용 */
    private int categoryId;                     /** 일상 게시글 카테고리 번호 */
    private List<MultipartFile> attachImages;   /** 일상 게시글 첨부 파일 목록 */
    private int hitCount;                       /** 일상 게시글 조회수 */
    private String tags;                        /** 일상 게시글 태그 이름들 */
}
