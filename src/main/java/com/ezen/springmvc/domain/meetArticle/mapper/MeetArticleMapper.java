package com.ezen.springmvc.domain.meetArticle.mapper;

import com.ezen.springmvc.domain.common.dto.SearchDto;
import com.ezen.springmvc.domain.meetArticle.dto.MeetArticleDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MeetArticleMapper {
    void createMeetArticle(MeetArticleDto meetArticleDto);
    MeetArticleDto findByMeetArticleId(MeetArticleDto meetArticleId);
    List<MeetArticleDto> findByAllMeetArticle(@Param("categoryId") int categoryId, @Param("searchDto") SearchDto searchDto);
    MeetArticleDto readMeetArticle(@Param("categoryId") int categoryId, @Param("meetArticleId") int meetArticleId);
    int findByMeetArticleCount(@Param("categoryId") int categoryId, @Param("searchDto") SearchDto searchDto);
//    void updateMeetArticle(MeetArticleDto meetArticleDto);
//    void deleteMeetArticle(MeetArticleDto meetArticleDto);
    void hitcount(MeetArticleDto meetArticleDto);
    List<MeetArticleDto> findByAllTagName(@Param("categoryId") int categoryId, @Param("tagName") String tagName, @Param("searchDto") SearchDto searchDto);
}