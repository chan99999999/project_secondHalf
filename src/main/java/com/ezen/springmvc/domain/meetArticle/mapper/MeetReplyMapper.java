package com.ezen.springmvc.domain.meetArticle.mapper;

import com.ezen.springmvc.domain.dailyarticle.dto.ReplyDto;
import com.ezen.springmvc.domain.meetArticle.dto.MeetReplyDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MeetReplyMapper {
    void createMeetReply(MeetReplyDto meetReplyDto);
    List<MeetReplyDto> findByMeetReplyAll(@Param("meetArticleId") int meetArticleId);
    int meetReplyCount(int meetArticleId);
}