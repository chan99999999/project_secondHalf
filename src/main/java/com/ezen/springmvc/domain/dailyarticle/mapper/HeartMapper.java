package com.ezen.springmvc.domain.dailyarticle.mapper;

import com.ezen.springmvc.domain.dailyarticle.dto.HeartDto;
import com.ezen.springmvc.domain.dailyarticle.dto.ReplyDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
/** heart 테이블 관련 Mapper */
public interface HeartMapper {

    /**
     * 좋아요 생성
     * @param dailyArticleId 일상 게시글 번호
     * @param memberId       회원 아이디
     */
    public void createHeart(@Param("dailyArticleId") int dailyArticleId, @Param("memberId") String memberId);

    /**
     * 좋아요 증가 업데이트
     * @param dailyArticleId 일상 게시글 번호
     * @param memberId       회원 아이디
     */
    public void plusHeart(@Param("dailyArticleId") int dailyArticleId, @Param("memberId") String memberId);

    /**
     * 좋아요 감소 업데이트
     * @param dailyArticleId 일상 게시글 번호
     * @param memberId       회원 아이디
     */
    public void minusHeart(@Param("dailyArticleId") int dailyArticleId, @Param("memberId") String memberId);

    /**
     * 회원 아이디별 좋아요 개수 조회
     * @param dailyArticleId 일상 게시글 번호
     * @param memberId       회원 아이디
     * @return 좋아요 개수
     */
    public int findHeartCount(@Param("dailyArticleId") int dailyArticleId, @Param("memberId") String memberId);

    /**
     * 일상 게시글별 좋아요 총 개수 조회
     * @param dailyArticleId 일상 게시글 번호
     * @return 좋아요 총 개수
     */
    public int findTotalHeartCount(int dailyArticleId);

    /**
     * 좋아요 존재 유무 반환을 위한
     * 일상 게시글 번호와 회원 아이디별 좋아요 개수 조회
     * @param dailyArticleId 일상 게시글 번호
     * @param memberId       회원 아이디
     * @return 좋아요 존재 유무
     */
    public int existHeart(@Param("dailyArticleId") int dailyArticleId, @Param("memberId") String memberId);
}
