package com.ezen.springmvc.domain.meetArticle.mapper;

import com.ezen.springmvc.domain.meetArticle.dto.TagDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TagMapper {
    void createTag(TagDto tagDto);
    TagDto findByTagId(int tagId);
    List<TagDto> findByAllTagName(@Param("categoryId") int categoryId, @Param("tagName") String tagName);
}
