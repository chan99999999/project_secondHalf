package com.ezen.springmvc.domain.meetArticle.mapper;

import com.ezen.springmvc.domain.meetArticle.dto.TagDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//@Mapper
public interface TagMapper {
    void createTag(TagDto tagDto);
    List<TagDto> findByTagAll();
    TagDto findByTagId(int meetArticleId);
}
