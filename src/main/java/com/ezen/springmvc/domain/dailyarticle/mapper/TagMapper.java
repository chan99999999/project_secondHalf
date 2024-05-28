package com.ezen.springmvc.domain.dailyarticle.mapper;

import com.ezen.springmvc.domain.dailyarticle.dto.FileDto;
import com.ezen.springmvc.domain.dailyarticle.dto.TagDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagMapper {

    /**
     * 태그 등록
     * @param tagDto 태그
     */
    public void createTag(TagDto tagDto);

    /**
     * 대그 번호로 태그 조회
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

}
