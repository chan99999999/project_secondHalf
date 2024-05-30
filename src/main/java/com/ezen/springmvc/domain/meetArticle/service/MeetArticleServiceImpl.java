package com.ezen.springmvc.domain.meetArticle.service;

import com.ezen.springmvc.domain.common.dto.SearchDto;
import com.ezen.springmvc.domain.meetArticle.dto.*;
import com.ezen.springmvc.domain.meetArticle.mapper.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MeetArticleServiceImpl implements MeetArticleService{
    private static final Logger log = LoggerFactory.getLogger(MeetArticleServiceImpl.class);
    private final MeetArticleMapper meetArticleMapper;
    private final MeetReplyMapper meetReplyMapper;
    private final TagMapper tagMapper;
    private final TagArticleMapper tagArticleMapper;

    @Override
    @Transactional
    public MeetArticleDto createMeetArticle(MeetArticleDto meetArticleDto, TagDto tagDto) {
        meetArticleMapper.createMeetArticle(meetArticleDto);
        tagMapper.createTag(tagDto);
        return meetArticleMapper.findByMeetArticleId(meetArticleDto.getMeetArticleId());
    }
    @Override
    public List<MeetArticleDto> findByAllMeetArticle(int categoryId, SearchDto searchDto) {
        return meetArticleMapper.findByAllMeetArticle(categoryId, searchDto);
    }

    @Override
    public MeetArticleDto readMeetArticle(int categoryId, int meetArticleId) {
        return meetArticleMapper.readMeetArticle(categoryId, meetArticleId);
    }

    @Override
    public int findByMeetArticleCount(int categoryId, SearchDto searchDto) {
        return meetArticleMapper.findByMeetArticleCount(categoryId, searchDto);
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
    public MeetArticleDto hitcount(MeetArticleDto meetArticleDto) {
        meetArticleMapper.hitcount(meetArticleDto);
        return meetArticleDto;
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
    public List<MeetArticleDto> findByAllTagName(int categoryId, String tagName, SearchDto searchDto) {
        List<TagDto> tags = tagMapper.findByAllTagName(categoryId, tagName);
        return tags.stream().map(tag -> {
            MeetArticleDto meetArticleDto = new MeetArticleDto();
            return meetArticleDto;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createReply(MeetReplyDto meetReplyDto) {
        meetReplyMapper.createReply(meetReplyDto);
    }

//    @Override
//    public void deleteReply(ReplyDto replyDto) {
//        replyMapper.deleteReply(replyDto);
//    }

    @Override
    public List<MeetReplyDto> replyList(int meetArticleId) {
        return meetReplyMapper.findByReplyAll(meetArticleId);
    }

    @Override
    public int replyCount(int meetArticleId) {
        return meetReplyMapper.replyCount(meetArticleId);
    }
}
