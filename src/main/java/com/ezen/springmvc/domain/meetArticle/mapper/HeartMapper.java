package com.ezen.springmvc.domain.meetArticle.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//@Mapper
public interface HeartMapper {

    /**
     * 좋아요 등록
     * @param heartDto 좋아요
     */
//    public void createHeart(HeartDto heartDto);

    /**
     * 좋아요 삭제
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

}
