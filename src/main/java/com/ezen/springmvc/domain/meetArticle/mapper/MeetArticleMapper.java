package com.ezen.springmvc.domain.meetArticle.mapper;

import com.ezen.springmvc.domain.meetArticle.dto.MeetArticleDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MeetArticleMapper {
    void createMeetArticle(MeetArticleDto meetArticleDto);
    MeetArticleDto findByMeetArticleId(int meetArticleId);
    List<MeetArticleDto> findByAllMeetArticle(@Param("categoryId") int categoryId);
    MeetArticleDto readMeetArticle(@Param("categoryId") int categoryId, @Param("meetArticleId") int meetArticleId);
//    void updateMeetArticle(MeetArticleDto meetArticleDto);
//    void deleteMeetArticle(MeetArticleDto meetArticleDto);
//    void hitcount(MeetArticleDto meetArticleDto);
}