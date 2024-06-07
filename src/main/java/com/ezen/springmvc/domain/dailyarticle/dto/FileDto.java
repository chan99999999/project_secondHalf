package com.ezen.springmvc.domain.dailyarticle.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
/** 파일 DTO */
public class FileDto {
    private int fileId;             /** 파일 번호 */
    private String fileName;        /** 파일 이름 */
    private String encryptedName;   /** 서버에 저장된 파일 이름 */
    private String filePath;        /** 파일 경로 */
    private int dailyArticleId;     /** 일상 게시글 번호 */
}
