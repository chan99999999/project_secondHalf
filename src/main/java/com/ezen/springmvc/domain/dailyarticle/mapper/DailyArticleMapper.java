package com.ezen.springmvc.domain.dailyarticle.mapper;

import com.ezen.springmvc.domain.dailyarticle.dto.DailyArticleDto;
import com.ezen.springmvc.domain.member.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

//@Repository
@Mapper
public interface DailyArticleMapper {

    /**
     * 신규 일상 게시글 등록
     * @param dailyArticleDto 일상 게시글
     */
    public void createDailyArticle(DailyArticleDto dailyArticleDto);

    /**
     * 일상 게시글 번호로 게시글 조회
     * @param dailyArticleId 일상 게시글 번호
     * @return 일상 게시글
     */
    public DailyArticleDto findByDailyArticleId(int dailyArticleId);

    /**
     * 일상 게시글 목록 반환
     * @param categoryId 카테고리 번호
     * @return 일상 게시글 목록
     */
    public List<DailyArticleDto> findByAllDailyArticle(@Param("categoryId") int categoryId);
}