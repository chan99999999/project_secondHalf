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
    public List<DailyArticleDto> findByAllDailyArticle(@Param("categoryId") int categoryId, @Param("searchDto") SearchDto searchDto);

    /**
     * 태그 이름 별 일상 게시글 목록 반환
     * @param categoryId 카테고리 번호
     * @param tagName 태그 이름
     * @return 일상 게시글 목록
     */
    public List<DailyArticleDto> findByAllTagName(@Param("categoryId") int categoryId, @Param("tagName") String tagName, @Param("searchDto") SearchDto searchDto);

    /**
     * 일상 게시글 상세보기
     * @param categoryId 카테고리 번호
     * @param dailyArticleId 일상 게시글 번호
     * @return 일상 게시글
     */
    public DailyArticleDto readDailyArticle(@Param("categoryId") int categoryId, @Param("dailyArticleId") int dailyArticleId);

    /**
     * 일상 게시글 개수 반환
     * @return 일상 게시글 개수
     */
    public int findDailyArticleCount(@Param("categoryId") int categoryId, @Param("searchDto") SearchDto searchDto);

    /**
     * 일상 게시글 삭제
     * @param categoryId 카테고리 번호
     * @param dailyArticleId 일상 게시글 번호
     */
    public void deleteDailyArticle(@Param("categoryId") int categoryId, @Param("dailyArticleId") int dailyArticleId);

    /**
     * 일상 게시글 수정
     * @param dailyArticleId 일상 게시글 번호
     * @param editedDailyArticleDto 수정된 일상 게시글
     */
    public void updateDailyArticle(@Param("dailyArticleId") int dailyArticleId,
                                   @Param("editedDailyArticleDto") DailyArticleDto editedDailyArticleDto);

}
