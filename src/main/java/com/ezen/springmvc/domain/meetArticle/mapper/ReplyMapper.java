package com.ezen.springmvc.domain.meetArticle.mapper;

import com.ezen.springmvc.domain.meetArticle.dto.ReplyDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReplyMapper {
    void createReply(ReplyDto replyDto);
    List<ReplyDto> findByReplyAll(@Param("meetArticleId") int meetArticleId);
    void updateReply(ReplyDto replyDto);
    void deleteReply(ReplyDto replyDto);
    void replyCount(ReplyDto replyDto);
}
