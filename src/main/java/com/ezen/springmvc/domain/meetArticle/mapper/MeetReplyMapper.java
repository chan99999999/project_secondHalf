package com.ezen.springmvc.domain.meetArticle.mapper;

import com.ezen.springmvc.domain.meetArticle.dto.meetReplyDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MeetReplyMapper {
    void createReply(meetReplyDto meetReplyDto);
    List<meetReplyDto> findByReplyAll(@Param("meetArticleId") int meetArticleId);
//    void updateReply(ReplyDto replyDto);
//    void deleteReply(ReplyDto replyDto);
    int replyCount(int meetArticleId);
}
