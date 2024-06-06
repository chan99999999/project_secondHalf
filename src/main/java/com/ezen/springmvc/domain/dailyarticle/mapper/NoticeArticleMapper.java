package com.ezen.springmvc.domain.dailyarticle.mapper;

import com.ezen.springmvc.domain.common.dto.SearchDto;
import com.ezen.springmvc.domain.dailyarticle.dto.DailyArticleDto;
import com.ezen.springmvc.domain.dailyarticle.dto.NoticeArticleDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoticeArticleMapper {
    /**
     * 신규 일상 게시글 생성
     * @param dailyArticleDto 일상 게시글
     */
    public void createNoticeArticle(NoticeArticleDto noticeArticleDto);

    /**
     * 일상 게시글 번호로 게시글 조회
     * @param dailyArticleId 일상 게시글 번호
     * @return 일상 게시글
     */
    public NoticeArticleDto findByNoticeArticleId(int noticeArticleId);

    public List<NoticeArticleDto> findByAllNoticeArticle(@Param("categoryId") int categoryId);

    public List<NoticeArticleDto> findByAllNoticeArticleTagName(@Param("categoryId") int categoryId, @Param("tagName") String tagName);

    /**
     * 일상 게시글 상세 조회
     * @param categoryId     카테고리 번호
     * @param dailyArticleId 일상 게시글 번호
     * @return 일상 게시글
     */
    public NoticeArticleDto readNoticeArticle(@Param("categoryId") int categoryId, @Param("noticeArticleId") int noticeArticleId);

    /**
     * 일상 게시글 조회수 업데이트
     * @param categoryId     카테고리 번호
     * @param dailyArticleId 일상 게시글 번호
     */
    public void updateNoticeArticleHitCount(@Param("categoryId") int categoryId,
                                           @Param("noticeArticleId") int noticeArticleId);

    /**
     * 일상 게시글 업데이트
     * @param dailyArticleId        일상 게시글 번호
     * @param editedDailyArticleDto 수정된 일상 게시글
     */
    public void updateNoticeArticle(@Param("noticeArticleId") int noticeArticleId,
                                   @Param("editedNoticeArticleDto") NoticeArticleDto editedNoticeArticleDto);

    /**
     * 일상 게시글 행 삭제
     * @param categoryId     카테고리 번호
     * @param dailyArticleId 일상 게시글 번호
     */
    public void deleteNoticeArticle(@Param("categoryId") int categoryId, @Param("noticeArticleId") int noticeArticleId);

}
