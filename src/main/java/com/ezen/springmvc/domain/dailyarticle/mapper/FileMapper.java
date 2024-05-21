package com.ezen.springmvc.domain.dailyarticle.mapper;

import com.ezen.springmvc.domain.dailyarticle.dto.DailyArticleDto;
import com.ezen.springmvc.domain.dailyarticle.dto.FileDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {

    /**
     * 파일 등록
     * @param fileDto 파일
     */
    public void createFileDto(FileDto fileDto);

    /**
     * 파일 번호로 파일 조회
     * @param fileId 파일 번호
     * @return 파일
     */
    public FileDto findByFileId(int fileId);
}
