package com.ezen.springmvc.domain.dailyarticle.mapper;

import com.ezen.springmvc.domain.dailyarticle.dto.FileDto;
import com.ezen.springmvc.domain.dailyarticle.dto.TagDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
/** tag 테이블 관련 Mapper */
public interface TagMapper {

    /**
     * 태그 생성
     * @param tagDto 태그
     */
    public void createTag(TagDto tagDto);

    /**
     * 태그 번호로 태그 조회
     * @param tagId 태그 번호
     * @return 태그
     */
    public TagDto findByTagId(int tagId);

    /**
     * 태그 이름으로 태그 조회
     * @param tagName
     * @return 태그
     */
    public TagDto findByTagName(String tagName);

    /**
     * 일상 게시글 번호로 태그 목록 조회
     * @param dailyArticleId 일상 게시글 번호
     * @return 태그 목록
     */
    public List<TagDto> findByDailyArticleId(int dailyArticleId);
}
