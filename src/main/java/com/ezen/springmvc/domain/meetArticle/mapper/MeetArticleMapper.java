package com.ezen.springmvc.domain.meetArticle.mapper;

import com.ezen.springmvc.domain.common.dto.SearchDto;
import com.ezen.springmvc.domain.meetArticle.dto.MeetArticleDto;
import com.ezen.springmvc.domain.meetArticle.dto.TagArticleDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MeetArticleMapper {
    List<MeetArticleDto> findByTitle(@Param("categoryId") int categoryId, @Param("searchDto") SearchDto searchDto);
    void createMeetArticle(MeetArticleDto meetArticleDto);
    List<MeetArticleDto> findByAllMeetArticle(@Param("categoryId") int categoryId);
    MeetArticleDto readMeetArticle(@Param("categoryId") int categoryId, @Param("meetArticleId") int meetArticleId);
    int findByMeetArticleCount(@Param("categoryId") int categoryId, @Param("searchDto") SearchDto searchDto);
    void hitcount(MeetArticleDto meetArticleDto);
    void participate(@Param("categoryId") int categoryId, @Param("meetArticleId") int meetArticleId, @Param("memberId") String memberId);
    void cancelParticipation(@Param("categoryId") int categoryId, @Param("meetArticleId") int meetArticleId, @Param("memberId") String memberId);
}