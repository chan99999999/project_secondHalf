package com.ezen.springmvc.domain.meetArticle.mapper;

import com.ezen.springmvc.domain.meetArticle.dto.TagArticleDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagArticleMapper {
    void createTagArticle(TagArticleDto tagArticleDto);
}
