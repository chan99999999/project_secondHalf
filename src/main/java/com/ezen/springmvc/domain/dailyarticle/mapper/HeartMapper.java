package com.ezen.springmvc.domain.dailyarticle.mapper;

import com.ezen.springmvc.domain.dailyarticle.dto.HeartDto;
import com.ezen.springmvc.domain.dailyarticle.dto.ReplyDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HeartMapper {

    /**
     * 좋아요 등록
     * @param dailyArticleId 일상 게시글 번호
     * @param memberId 회원 아이디
     */
    public void createHeart(@Param("dailyArticleId") int dailyArticleId, @Param("memberId") String memberId);

    /**
     * 좋아요 업데이트
     * @param dailyArticleId 일상 게시글 번호
     * @param memberId 회원 아이디
     */
    public void plusHeart(@Param("dailyArticleId") int dailyArticleId, @Param("memberId") String memberId);

    public void minusHeart(@Param("dailyArticleId") int dailyArticleId, @Param("memberId") String memberId);
    /**
     * 좋아요 개수 반환
     * @param dailyArticleId 일상 게시글 번호
     * @param memberId 회원 아이디
     * @return 좋아요 개수
     */
    public int findHeartCount(@Param("dailyArticleId") int dailyArticleId, @Param("memberId") String memberId);

    /**
     * 좋아요 존재 유무 반환
     * @param dailyArticleId 일상 게시글 번호
     * @param memberId 회원 아이디
     * @return
     */
    public int existHeart(@Param("dailyArticleId") int dailyArticleId, @Param("memberId") String memberId);
}
