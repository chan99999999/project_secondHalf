package com.ezen.springmvc.domain.meetArticle.mapper;

import com.ezen.springmvc.domain.meetArticle.dto.MeetReplyDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MeetReplyMapper {
    void createReply(MeetReplyDto meetReplyDto);
    List<MeetReplyDto> findByReplyAll(@Param("meetArticleId") int meetArticleId);
//    void updateReply(ReplyDto replyDto);
//    void deleteReply(ReplyDto replyDto);
    int replyCount(int meetArticleId);
}
