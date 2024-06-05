package com.ezen.springmvc.domain.meetArticle.mapper;

import com.ezen.springmvc.domain.meetArticle.dto.MeetTagArticleDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MeetTagArticleMapper {
    void createMeetTagArticle(MeetTagArticleDto meetTagArticleDto);
    void readMeetTagArticle(MeetTagArticleDto meetTagArticleDto);
}
