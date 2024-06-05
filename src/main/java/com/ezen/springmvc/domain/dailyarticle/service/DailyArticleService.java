package com.ezen.springmvc.domain.dailyarticle.service;

import com.ezen.springmvc.domain.common.dto.SearchDto;
import com.ezen.springmvc.domain.dailyarticle.dto.*;
import com.ezen.springmvc.domain.member.dto.MemberDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 일상 게시글 관련 비즈니스 로직 처리 및 트랜잭션 관리
 */
public interface DailyArticleService {

    /**
     * 신규 일상 게시글 등록
     * @param dailyArticleDto 일상 게시글
     * @param fileList        파일 목록
     * @return 일상 게시글
     */
    public DailyArticleDto writeDailyArticle(DailyArticleDto dailyArticleDto, List<FileDto> fileList);

    public NoticeArticleDto writeNoticeArticle(NoticeArticleDto noticeArticleDto, List<FileDto> fileList);

    /**
     * 검색 조건에 따른 일상 게시글 목록 반환
     * @param categoryId 카테고리 번호
     * @param searchDto  검색
     * @return 일상 게시글 목록
     */
    public List<DailyArticleDto> getDailyArticles(int categoryId, SearchDto searchDto);

    public List<NoticeArticleDto> getNoticeArticles(int categoryId);

    /**
     * 태그 이름 및 검색 조건에 따른 일상 게시글 목록 반환
     * @param categoryId 카테고리 번호
     * @param tagName    태그 이름
     * @param searchDto  검색
     * @return 일상 게시글 목록
     */
    public List<DailyArticleDto> getDailyArticlesByTagName(int categoryId, String tagName, SearchDto searchDto);

    public List<DailyArticleDto> getAdminDailyArticles(int categoryId, SearchDto searchDto);

    public List<DailyArticleDto> getAdminDailyArticlesByTagName(int categoryId, String tagName, SearchDto searchDto);

    public List<NoticeArticleDto> getNoticeArticlesByTagName(int categoryId, String tagName);


    /**
     * 일상 게시글 상세보기
     * @param categoryId     카테고리 번호
     * @param dailyArticleId 일상 게시글 번호
     * @return 일상 게시글
     */
    public DailyArticleDto getDailyArticle(int categoryId, int dailyArticleId);

    public NoticeArticleDto getNoticeArticle(int categoryId, int noticeArticleId);

    /**
     * 검색 조건에 따른 일상 게시글 개수 반환
     * @param categoryId 카테고리 번호
     * @param searchDto  검색
     * @return 일상 게시글 개수
     */
    public int getDailyArticleCount(int categoryId, SearchDto searchDto);

    public int getAdminDailyArticleCount(int categoryId, SearchDto searchDto);

    /**
     * 일상 게시글 수정
     * @param dailyArticleId        일상 게시글 번호
     * @param editedDailyArticleDto 수정된 일상 게시글
     */
    public void editDailyArticle(int dailyArticleId, DailyArticleDto editedDailyArticleDto);

    public void editNoticeArticle(int noticeArticleId, NoticeArticleDto editedNoticeArticle);

    /**
     * 일상 게시글 삭제
     * @param categoryId     카테고리 번호
     * @param dailyArticleId 일상 게시글 번호
     */
    public void removeDailyArticle(int categoryId, int dailyArticleId);

    public void removeNoticeArticle(int categoryId, int noticeArticleId);


    /**
     * 일상 게시글 번호로 파일 목록 반환
     * @param dailyArticleId 일상 게시글 번호
     * @return 파일 목록
     */
    public List<FileDto> getFiles(int dailyArticleId);

    /**
     * 파일 목록 반환
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
     * 댓글 개수 반환
     * @param dailyArticleId 일상 게시글 번호
     * @return 댓글 개수
     */
    public int getReplyCount(int dailyArticleId);

    /**
     * 댓글 수정
     * @param dailyArticleId 일상 게시글 번호
     * @param replyId        댓글 번호
     * @param content        댓글 내용
     */
    public void editReply(int dailyArticleId, int replyId, String content);

    /**
     * 댓글 삭제
     * @param dailyArticleId 일상 게시글 번호
     * @param replyId        댓글 번호
     */
    public void removeReply(int dailyArticleId, int replyId);

    /**
     * 좋아요 등록 및 업데이트
     * @param dailyArticleId 일상 게시글 번호
     * @param memberId       회원 아이디
     */
    public boolean insertAndUpdateHeart(int dailyArticleId, String memberId, boolean checked);

    /**
     * 회원 아이디별 좋아요 개수 반환
     * @param dailyArticleId 일상 게시글 번호
     * @param memberId       회원 아이디
     * @return 좋아요 개수
     */
    public int getHeartCount(int dailyArticleId, String memberId);

    /**
     * 일상 게시글별 좋아요 총 개수 반환
     * @param dailyArticleId 일상 게시글 번호
     * @return 좋아요 총 개수
     */
    public int getTotalHeartCount(int dailyArticleId);

    /**
     * 좋아요 존재 유무 반환을 위한
     * 일상 게시글 번호와 회원 아이디별 좋아요 개수 반환
     * @param dailyArticleId 일상 게시글 번호
     * @param memberId       회원 아이디
     * @return 좋아요 존재 유무
     */
    public int getExistHeart(int dailyArticleId, String memberId);

    /**
     * 태그 등록
     * @param tagDto 태그
     */
    public void getTag(TagDto tagDto);

    /**
     * 태그 게시글 등록
     * @param tagId          태그 번호
     * @param articleId      게시글 번호
     */
    public void getTagArticle(int tagId, int articleId);

    /**
     * 태그 이름으로 기존 태그 또는 새로운 태그 반환
     * @param tagName 태그 이름
     * @return 태그
     */
    public TagDto getOrCreateTag(String tagName);
}
