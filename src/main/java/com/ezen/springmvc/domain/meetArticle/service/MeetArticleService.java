package com.ezen.springmvc.domain.meetArticle.service;

import com.ezen.springmvc.domain.meetArticle.dto.CategoryDto;
import com.ezen.springmvc.domain.meetArticle.dto.MeetArticleDto;
import com.ezen.springmvc.domain.meetArticle.dto.ReplyDto;
import com.ezen.springmvc.domain.meetArticle.dto.TagDto;

import java.util.List;

public interface MeetArticleService {
//    카테고리 목록
    List<CategoryDto> categoryList();
//    모임 게시글 등록
    MeetArticleDto createMeetArticle(MeetArticleDto meetArticleDto);
//    게시글 전체 목록
    List<MeetArticleDto> findByAllMeetArticle(int categoryId);
//    모임 게시글 상세보기
    void readMeetArticle(MeetArticleDto meetArticleDto);
//    모임 게시글 수정
    void updateMeetArticle(MeetArticleDto meetArticleDto);
//    모임 게시글 삭제
    void deleteMeetArticle(MeetArticleDto meetArticleDto);
//    게시글 조회수
//    void hitcount(MeetArticleDto meetArticleDto);
//    태그별 모임 게시글 조회
//    TagDto findByTagName(TagDto tagDto);
//    태그 목록
//    List<TagDto> tagList();
//    댓글 등록
    void createReply(ReplyDto replyDto);
//    댓글 수정
    void updateReply(ReplyDto replyDto);
//    댓글 삭제
    void deleteReply(ReplyDto replyDto);
//    댓글 목록
    List<ReplyDto> replyList(int meetArticleId);
//    댓글 갯수
    void replyCount(ReplyDto replyDto);
}
