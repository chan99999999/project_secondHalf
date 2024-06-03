package com.ezen.springmvc.domain.meetArticle.service;

import com.ezen.springmvc.domain.common.dto.SearchDto;
import com.ezen.springmvc.domain.dailyarticle.dto.ReplyDto;
import com.ezen.springmvc.domain.meetArticle.dto.MeetArticleDto;
import com.ezen.springmvc.domain.meetArticle.dto.MeetReplyDto;
import com.ezen.springmvc.domain.meetArticle.dto.TagArticleDto;
import com.ezen.springmvc.domain.meetArticle.dto.TagDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MeetArticleService {
//    게시글 전체 목록
    List<MeetArticleDto> findByAllMeetArticle(int categoryId);
//    모임 게시글 상세보기
    MeetArticleDto readMeetArticle(int categoryId, int meetArticleId);
//    모임 게시글 갯수 반환 (페이징처리)
    int findByMeetArticleCount(int categoryId, SearchDto searchDto);
//    모임 게시글 등록
    void addMeet(MeetArticleDto meetArticleDto);
//    모임 게시글 검색
    List<MeetArticleDto> findByTitle(String title, SearchDto searchDto);
//    모임 게시글 수정
//    void updateMeetArticle(int meetArticleId, MeetArticleDto meetArticleDto);
//    모임 게시글 삭제
//    void deleteMeetArticle(MeetArticleDto meetArticleDto);
//    게시글 조회수
    MeetArticleDto hitcount(MeetArticleDto meetArticleDto);
//    태그 게시글 삭제
//    void deleteTagArticle(TagArticleDto tagArticleDto);
//    태그 이름으로 게시글 목록 반환
    List<MeetArticleDto> findByAllTagName(@Param("categoryId") int categoryId, @Param("tagName") String tagName, @Param("searchDto") SearchDto searchDto);
//    태그 삭제
//    void deleteTag(TagDto tagDto);
//    댓글 등록
    void createReply(MeetReplyDto meetReplyDto);
//    댓글 삭제
//    void deleteReply(MeetReplyDto meetReplyDto);
//    댓글 목록
    List<MeetReplyDto> replyList(int meetArticleId);
//    댓글 갯수
    int replyCount(int meetArticleId);
}
