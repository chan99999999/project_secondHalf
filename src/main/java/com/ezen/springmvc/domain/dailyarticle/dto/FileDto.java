package com.ezen.springmvc.domain.dailyarticle.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class FileDto {
    private int fileId;
    private String fileName;
    private String encryptedName;
    private String filePath;
    private int dailyArticleId;
}
