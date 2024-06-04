package com.ezen.springmvc.domain.dailyarticle.mapper;

import com.ezen.springmvc.domain.dailyarticle.dto.TagArticleDto;
import com.ezen.springmvc.domain.dailyarticle.dto.TagDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
/** tag_article 테이블(매핑 테이블) 관련 Mapper */
public interface TagArticleMapper {

    /**
     * 태그 게시글 생성
     * @param tagId          태그 번호
     * @param dailyArticleId 일상 게시글 번호
     */
    public void createTagArticle(@Param("tagId") int tagId, @Param("dailyArticleId") int dailyArticleId);
}
