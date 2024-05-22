package com.ezen.springmvc.domain.dailyarticle.mapper;

import com.ezen.springmvc.domain.dailyarticle.dto.DailyArticleDto;
import com.ezen.springmvc.domain.dailyarticle.dto.FileDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FileMapper {

    /**
     * 파일 등록
     * @param fileDto 파일
     */
    public void createFileDto(FileDto fileDto);

    /**
     * 일상 게시글 번호로 파일 조회
     * @param dailyArticleId 일상 게시글 번호
     * @return 파일
     */
    public FileDto findByFileId(int dailyArticleId);

    /**
     * 파일 목록 조회
     * @return 파일 목록
     */
    public List<FileDto> findByAllFile();
}
