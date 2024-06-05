package com.ezen.springmvc.domain.dailyarticle.mapper;

import com.ezen.springmvc.domain.common.dto.SearchDto;
import com.ezen.springmvc.domain.dailyarticle.dto.DailyArticleDto;
import com.ezen.springmvc.domain.dailyarticle.dto.FileDto;
import com.ezen.springmvc.domain.dailyarticle.dto.TagDto;
import com.ezen.springmvc.domain.member.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Mapper
/** daily_article 테이블 관련 Mapper */
public interface DailyArticleMapper {

    /**
     * 신규 일상 게시글 생성
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
     * 검색 조건에 따른 일상 게시글 목록 조회
     * @param categoryId 카테고리 번호
     * @param searchDto  검색
     * @return
     */
    public List<DailyArticleDto> findByAllDailyArticle(@Param("categoryId") int categoryId, @Param("searchDto") SearchDto searchDto);

    /**
     * 태그 이름 및 검색 조건에 따른 일상 게시글 목록 조회
     * @param categoryId 카테고리 번호
     * @param tagName    태그 이름
     * @param searchDto  검색
     * @return 일상 게시글 목록
     */
    public List<DailyArticleDto> findByAllTagName(@Param("categoryId") int categoryId, @Param("tagName") String tagName, @Param("searchDto") SearchDto searchDto);

    public List<DailyArticleDto> findByAllAdminDailyArticles(@Param("categoryId") int categoryId, @Param("searchDto") SearchDto searchDto);

    public List<DailyArticleDto> findByAllAdminTagName(@Param("categoryId") int categoryId, @Param("tagName") String tagName, @Param("searchDto") SearchDto searchDto);

    /**
     * 일상 게시글 상세 조회
     * @param categoryId     카테고리 번호
     * @param dailyArticleId 일상 게시글 번호
     * @return 일상 게시글
     */
    public DailyArticleDto readDailyArticle(@Param("categoryId") int categoryId, @Param("dailyArticleId") int dailyArticleId);

    /**
     * 검색 조건에 따른 일상 게시글 개수 조회
     * @param categoryId 카테고리 번호
     * @param searchDto  검색
     * @return 일상 게시글 개수
     */
    public int findDailyArticleCount(@Param("categoryId") int categoryId, @Param("searchDto") SearchDto searchDto);

    public int findAdminDailyArticleCount(@Param("categoryId") int categoryId, @Param("searchDto") SearchDto searchDto);

    /**
     * 일상 게시글 조회수 업데이트
     * @param categoryId     카테고리 번호
     * @param dailyArticleId 일상 게시글 번호
     */
    public void updateDailyArticleHitCount(@Param("categoryId") int categoryId,
                                           @Param("dailyArticleId") int dailyArticleId);

    /**
     * 일상 게시글 업데이트
     * @param dailyArticleId        일상 게시글 번호
     * @param editedDailyArticleDto 수정된 일상 게시글
     */
    public void updateDailyArticle(@Param("dailyArticleId") int dailyArticleId,
                                   @Param("editedDailyArticleDto") DailyArticleDto editedDailyArticleDto);

    /**
     * 일상 게시글 행 삭제
     * @param categoryId     카테고리 번호
     * @param dailyArticleId 일상 게시글 번호
     */
    public void deleteDailyArticle(@Param("categoryId") int categoryId, @Param("dailyArticleId") int dailyArticleId);
}
