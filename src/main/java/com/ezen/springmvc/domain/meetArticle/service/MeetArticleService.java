package com.ezen.springmvc.domain.meetArticle.service;

import com.ezen.springmvc.domain.meetArticle.dto.MeetArticleDto;
import com.ezen.springmvc.domain.meetArticle.dto.ReplyDto;
import com.ezen.springmvc.domain.meetArticle.dto.TagArticleDto;
import com.ezen.springmvc.domain.meetArticle.dto.TagDto;

import java.util.List;

public interface MeetArticleService {
//    모임 게시글 등록
    MeetArticleDto createMeetArticle(MeetArticleDto meetArticleDto);
//    게시글 전체 목록
    List<MeetArticleDto> findByAllMeetArticle(int categoryId);
//    모임 게시글 상세보기
    MeetArticleDto readMeetArticle(int categoryId, int meetArticleId);
//    모임 게시글 수정
//    void updateMeetArticle(MeetArticleDto meetArticleDto);
//    모임 게시글 삭제
//    void deleteMeetArticle(MeetArticleDto meetArticleDto);
//    게시글 조회수
    void hitcount(int meetArticleId);
//    태그 등록
    void creatTag(TagDto tagDto);
//    태그 이름으로 게시글 목록 반환
    List<MeetArticleDto> findByTagName(int categoryId, String tagName);
//    태그 게시글 생성
    void createTagArticle(TagArticleDto tagArticleDto);
//    댓글 등록
    MeetArticleDto createReply(ReplyDto replyDto);
//    댓글 삭제
//    void deleteReply(ReplyDto replyDto);
//    댓글 목록
    List<ReplyDto> replyList(int meetArticleId);
//    댓글 갯수
    int replyCount(int meetArticleId);
}
