package com.ezen.springmvc.domain.dailyarticle.service;

import com.ezen.springmvc.domain.dailyarticle.dto.*;
import com.ezen.springmvc.domain.member.dto.MemberDto;
import org.apache.ibatis.annotations.Param;

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
    public DailyArticleDto writeDailyArticle(DailyArticleDto dailyArticleDto, FileDto fileDto, TagDto tagDto);

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

    /**
     * 일상 게시글 번호로 파일 조회
     * @param dailyArticleId 일상 게시글 번호
     * @return 파일
     */
    public FileDto getFile(int dailyArticleId);

    /**
     * 파일 목록 조회
     * @return 파일 목록
     */
    public List<FileDto> getFiles();

    /**
     * 댓글 등록
     * @param replyDto 댓글
     */
    public void writeReply(ReplyDto replyDto);

    /**
     * 댓글 목록 반환
     * @param dailyArticleId 일상 게시글 번호
     * @return 댓글 목록
     */
    public List<ReplyDto> getReplyList(int dailyArticleId);

    /**
     * 댓글 수 반환
     * @param dailyArticleId 일상 게시글 번호
     * @return 댓글 수
     */
    public int getReplyCount(int dailyArticleId);

    /**
     * 좋아요 등록
     * @param heartDto 좋아요
     */
    public void clickHeart(HeartDto heartDto);

    /**
     * 좋아요 등록 및 업데이트
     * @param dailyArticleId 일상 게시글 번호
     * @param memberId 회원 아이디
     */
    public boolean insertAndUpdateHeart(int dailyArticleId, String memberId, boolean checked);

    /**
     * 좋아요 개수 반환
     * @param dailyArticleId 일상 게시글 번호
     * @param memberId 회원 아이디
     * @return 좋아요 개수
     */
    public int getHeartCount(int dailyArticleId, String memberId);

    /**
     * 좋아요 행의 개수 반환
     * @param dailyArticleId 일상 게시글 번호
     * @param memberId 회원 아이디
     * @return 좋아요 존재 유무
     */
    public int getExistHeart(int dailyArticleId, String memberId);

    /**
     * 좋아요 총 개수 반환
     * @param dailyArticleId 일상 게시글 번호
     * @return 좋아요 총 개수
     */
    public int getTotalHeartCount(int dailyArticleId);

    /**
     * 태그 생성
     * @param tagDto 태그
     * @param tagId 태그 번호
     * @return 태그
     */
    public TagDto getTag(TagDto tagDto, int tagId);

    /**
     * 태그 게시글 생성
     * @param tagArticleDto 태그 게시글
     */
    public void getTagArticle(TagArticleDto tagArticleDto);

}
