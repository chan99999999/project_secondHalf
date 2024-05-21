package com.ezen.springmvc.domain.dailyarticle.service;

import com.ezen.springmvc.domain.dailyarticle.dto.DailyArticleDto;
import com.ezen.springmvc.domain.dailyarticle.dto.FileDto;
import com.ezen.springmvc.domain.member.dto.MemberDto;

import java.util.List;

/**
 * 일상 게시글 관련 비즈니스 로직 처리 및 트랜잭션 관리
 */
public interface DailyArticleService {
    /**
     * 일상 게시글 등록
     * @param dailyArticleDto 일상 게시글
     * @return 일상 게시글
     */
    public DailyArticleDto writeDailyArticle(DailyArticleDto dailyArticleDto, FileDto fileDto);

    /**
     * 일상 게시글 목록 반환
     * @param categoryId 카테고리 번호
     * @return 일상 게시글 목록
     */
    public List<DailyArticleDto> getDailyArticles(int categoryId);

    /**
     * 일상 게시글 상세 보기
     * @param categoryId 카테고리 번호
     * @param dailyArticleId 일상 게시글 번호
     * @return 일상 게시글
     */
    public DailyArticleDto getDailyArticle(int categoryId, int dailyArticleId);
}
