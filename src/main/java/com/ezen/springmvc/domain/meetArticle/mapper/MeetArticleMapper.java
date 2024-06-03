package com.ezen.springmvc.domain.meetArticle.mapper;

import com.ezen.springmvc.domain.common.dto.SearchDto;
import com.ezen.springmvc.domain.meetArticle.dto.MeetArticleDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MeetArticleMapper {
    void createMeetArticle(MeetArticleDto meetArticleDto);
    List<MeetArticleDto> findByAllMeetArticle(@Param("categoryId") int categoryId);
    MeetArticleDto readMeetArticle(@Param("categoryId") int categoryId, @Param("meetArticleId") int meetArticleId);
    int findByMeetArticleCount(@Param("categoryId") int categoryId, @Param("searchDto") SearchDto searchDto);
    void updateMeetArticle(@Param("meetArticleId") int meetArticleId, @Param("meetArticleDto") MeetArticleDto meetArticleDto);
    void deleteMeetArticle(@Param("categoryId") int categoryId, @Param("meetArticleId") int meetArticleId);
    void hitcount(MeetArticleDto meetArticleDto);
}