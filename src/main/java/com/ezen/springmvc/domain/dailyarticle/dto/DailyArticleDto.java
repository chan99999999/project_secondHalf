package com.ezen.springmvc.domain.dailyarticle.dto;

import lombok.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class DailyArticleDto {
    private int dailyArticleId;
    private String title;
    private String content;
    private String regdate;
    private int hitCount;
    private int categoryId;
    private String memberId;
    private String encryptedName;
    private int totalHeartCount;
    private String tagNames;
}
