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
public class DailyArticleForm {
    private int dailyArticleId;
    private String memberId;
    private String regdate;
    private String title;
    private String content;
    private int categoryId;
    private MultipartFile attachImage;
    private int hitCount;
    private String tags;
}
