package com.ezen.springmvc.domain.meetArticle.service;

import com.ezen.springmvc.domain.common.dto.SearchDto;
import com.ezen.springmvc.domain.meetArticle.dto.MeetArticleDto;
import com.ezen.springmvc.domain.meetArticle.dto.MeetReplyDto;
import com.ezen.springmvc.domain.meetArticle.dto.TagArticleDto;
import com.ezen.springmvc.domain.meetArticle.dto.TagDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MeetArticleService {
//    모임 게시글 등록
    MeetArticleDto createMeetArticle(MeetArticleDto meetArticleDto, TagDto tagDto);
//    게시글 전체 목록
    List<MeetArticleDto> findByAllMeetArticle(int categoryId, SearchDto searchDto);
//    모임 게시글 상세보기
    MeetArticleDto readMeetArticle(int categoryId, int meetArticleId);
//    모임 게시글 갯수 반환 (페이징처리)
      int findByMeetArticleCount(int categoryId, SearchDto searchDto);
//    모임 게시글 수정
//    void updateMeetArticle(MeetArticleDto meetArticleDto);
//    모임 게시글 삭제
//    void deleteMeetArticle(MeetArticleDto meetArticleDto);
//    게시글 조회수
    MeetArticleDto hitcount(MeetArticleDto meetArticleDto);
//    태그 등록
    void creatTag(TagDto tagDto);
//    태그 이름으로 게시글 목록 반환
    List<MeetArticleDto> findByAllTagName(@Param("categoryId") int categoryId, @Param("tagName") String tagName, @Param("searchDto") SearchDto searchDto);
//    태그 게시글 생성
    void createTagArticle(TagArticleDto tagArticleDto);
//    댓글 등록
    void createReply(MeetReplyDto meetReplyDto);
//    댓글 삭제
//    void deleteReply(ReplyDto replyDto);
//    댓글 목록
    List<MeetReplyDto> replyList(int meetArticleId);
//    댓글 갯수
    int replyCount(int meetArticleId);
}
