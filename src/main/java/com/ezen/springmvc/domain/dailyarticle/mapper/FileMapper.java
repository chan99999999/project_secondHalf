package com.ezen.springmvc.domain.dailyarticle.mapper;

import com.ezen.springmvc.domain.dailyarticle.dto.DailyArticleDto;
import com.ezen.springmvc.domain.dailyarticle.dto.FileDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
/** attach_file 테이블 관련 Mapper */
public interface FileMapper {

    /**
     * 파일 생성
     * @param fileDto 파일
     */
    public void createFileDto(FileDto fileDto);

    /**
     * 다중 파일 생성
     * @param fileList 파일 목록
     */
    public void createFileList(List<FileDto> fileList);

    /**
     * 일상 게시글 번호로 파일 조회
     * @param dailyArticleId 일상 게시글 번호
     * @return 파일
     */
    public List<FileDto> findByFileId(int dailyArticleId);

    /**
     * 파일 목록 조회
     * @return 파일 목록
     */
    public List<FileDto> findByAllFile();
}
