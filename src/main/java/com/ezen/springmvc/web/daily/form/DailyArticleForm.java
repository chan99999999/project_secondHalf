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
    private int dailyArticleId;
    private String memberId;
    private String regdate;
    private String title;
    private String content;
    private int categoryId;
    private List<MultipartFile> attachImages;
    private int hitCount;
    private String tags;
}
