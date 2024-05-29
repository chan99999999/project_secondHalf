package com.ezen.springmvc.domain.meetArticle.mapper;

import com.ezen.springmvc.domain.meetArticle.dto.TagDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagMapper {
    void createTag(TagDto tagDto);
    TagDto findByTagId(int tagId);
    TagDto findByTagName(String tagName);
}
