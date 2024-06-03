package com.ezen.springmvc.domain.dailyarticle.mapper;

import com.ezen.springmvc.domain.dailyarticle.dto.DailyArticleDto;
import com.ezen.springmvc.domain.dailyarticle.dto.ReplyDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReplyMapper {

    /**
     * 댓글 등록
     * @param replyDto 댓글
     */
    public void createReply(ReplyDto replyDto);

    /**
     * 댓글 목록 반환
     * @param dailyArticleId 일상 게시글 번호
     * @return 댓글 목록
     */
    public List<ReplyDto> findByReplyAll(@Param("dailyArticleId") int dailyArticleId);

    /**
     * 댓글 수 반환
     * @param dailyArticleId 일상 게시글 번호
     * @return 댓글 수
     */
    public int findReplyCount(int dailyArticleId);

    /**
     * 댓글 삭제
     * @param dailyArticleId 일상 게시글 번호
     * @param replyId 댓글 번호
     */
    public void deleteReply(@Param("dailyArticleId")int dailyArticleId, @Param("replyId")int replyId);

    /**
     * 댓글 수정
     * @param dailyArticleId 일상 게시글 번호
     * @param replyId 댓글 번호
     * @param content 댓글 내용
     */
    public void updateReply(@Param("dailyArticleId")int dailyArticleId, @Param("replyId")int replyId, @Param("content") String content);
}
