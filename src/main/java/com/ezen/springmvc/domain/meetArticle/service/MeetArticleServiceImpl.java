package com.ezen.springmvc.domain.meetArticle.service;

import com.ezen.springmvc.domain.meetArticle.dto.MeetArticleDto;
import com.ezen.springmvc.domain.meetArticle.dto.ReplyDto;
import com.ezen.springmvc.domain.meetArticle.dto.TagArticleDto;
import com.ezen.springmvc.domain.meetArticle.dto.TagDto;
import com.ezen.springmvc.domain.meetArticle.mapper.MeetArticleMapper;
import com.ezen.springmvc.domain.meetArticle.mapper.ReplyMapper;
import com.ezen.springmvc.domain.meetArticle.mapper.TagArticleMapper;
import com.ezen.springmvc.domain.meetArticle.mapper.TagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MeetArticleServiceImpl implements MeetArticleService{
    private final MeetArticleMapper meetArticleMapper;
    private final ReplyMapper replyMapper;
    private final TagMapper tagMapper;
    private final TagArticleMapper tagArticleMapper;

    @Override
    public MeetArticleDto createMeetArticle(MeetArticleDto meetArticleDto) {
        meetArticleMapper.createMeetArticle(meetArticleDto);
//        tagDto.setMeetArticleId(meetArticleDto.getMeetArticleId());
//        tagMapper.createTag(tagDto);
        return meetArticleMapper.findByMeetArticleId(meetArticleDto.getMeetArticleId());
    }
    @Override
    public List<MeetArticleDto> findByAllMeetArticle(int categoryId) {
        return meetArticleMapper.findByAllMeetArticle(categoryId);
    }

    @Override
    public MeetArticleDto readMeetArticle(int categoryId, int meetArticleId) {
        return meetArticleMapper.readMeetArticle(categoryId, meetArticleId);
    }

//    @Override
//    public void updateMeetArticle(MeetArticleDto meetArticleDto) {
//        meetArticleMapper.updateMeetArticle(meetArticleDto);
//    }

//    @Override
//    public void deleteMeetArticle(MeetArticleDto meetArticleDto) {
//        meetArticleMapper.deleteMeetArticle(meetArticleDto);
//    }

    @Override
    public void hitcount(int meetArticleId) {
        meetArticleMapper.hitcount(meetArticleId);
    }

    @Override
    public void creatTag(TagDto tagDto) {
        tagMapper.createTag(tagDto);
    }

    @Override
    public void createTagArticle(TagArticleDto tagArticleDto) {
        tagArticleMapper.createTagArticle(tagArticleDto);
    }

    @Override
    public List<MeetArticleDto> findByTagName(int categoryId, String tagName) {
        return meetArticleMapper.findByAllTagName(categoryId, tagName);
    }

    @Override
    public MeetArticleDto createReply(ReplyDto replyDto) {
        replyMapper.createReply(replyDto);
        return null;
    }

//    @Override
//    public void deleteReply(ReplyDto replyDto) {
//        replyMapper.deleteReply(replyDto);
//    }

    @Override
    public List<ReplyDto> replyList(int meetArticleId) {
        return replyMapper.findByReplyAll(meetArticleId);
    }

    @Override
    public int replyCount(int meetArticleId) {
        return replyMapper.replyCount(meetArticleId);
    }
}
