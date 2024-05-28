package com.ezen.springmvc.domain.dailyarticle.mapper;

import com.ezen.springmvc.domain.dailyarticle.dto.TagArticleDto;
import com.ezen.springmvc.domain.dailyarticle.dto.TagDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagArticleMapper {

    /**
     * 태그 게시글 등록
     * @param tagArticleDto 태그 게시글
     */
    public void createTagArticle(TagArticleDto tagArticleDto);
}
