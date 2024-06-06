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

@RequiredArgsConstructor
@Service
public class MeetArticleServiceImpl implements MeetArticleService {
    private static final Logger log = LoggerFactory.getLogger(MeetArticleServiceImpl.class);
    private final MeetArticleMapper meetArticleMapper;
    private final MeetReplyMapper meetReplyMapper;
    private final MeetTagMapper meetTagMapper;
    private final MeetTagArticleMapper meetTagArticleMapper;

    // 모임 등록 서비스
    @Override
    @Transactional
    public void addMeet(MeetArticleDto meetArticleDto) {
        // 모임게시글 테이블에 신규 게시글 등록 
        meetArticleMapper.createMeetArticle(meetArticleDto);
        // 태그게시글 테이블에 신규 태그게시글 등록
        String[] tags = meetArticleDto.getTags();
        if (tags != null) {
            for (String tagName : tags) {
                tagName = tagName.trim();
                MeetTagDto meetTagDto = MeetTagDto.builder()
                        .tagName(tagName)
                        .build();
                // 태그 등록 및 해당 태그의 tag_id 가져오기
                meetTagMapper.createMeetTag(meetTagDto);

                int tagId = meetTagDto.getTagId();

                MeetTagArticleDto meetTagArticleDto = MeetTagArticleDto.builder()
                        .tagId(tagId)
                        .meetArticleId(meetArticleDto.getMeetArticleId())
                        .build();
                meetTagArticleMapper.createMeetTagArticle(meetTagArticleDto);
                // 테그 테이블에 신규 태그 등록
            }
        }
    }

    @Override
    public List<MeetTagDto> findByAllMeetTagName(String tagName) {
        return meetTagMapper.findByAllMeetTagName(tagName);
    }

    @Override
    public List<MeetArticleDto> findByAllMeetArticle(int categoryId) {
        return meetArticleMapper.findByAllMeetArticle(categoryId);
    }

    @Override
    public List<MeetArticleDto> findByMeetTitle(int categoryId, SearchDto searchDto) {
        return meetArticleMapper.findByMeetTitle(categoryId, searchDto);
    }

    @Override
    public MeetArticleDto readMeetArticle(int categoryId, int meetArticleId) {
        return meetArticleMapper.readMeetArticle(categoryId, meetArticleId);
    }

    @Override
    public int findByMeetArticleCount(int categoryId, SearchDto searchDto) {
        return meetArticleMapper.findByMeetArticleCount(categoryId, searchDto);
    }

    @Override
    public MeetArticleDto meetHitcount(MeetArticleDto meetArticleDto) {
        meetArticleMapper.meetHitcount(meetArticleDto);
        return meetArticleDto;
    }

    @Override
    @Transactional
    public void createMeetReply(MeetReplyDto meetReplyDto) {
        meetReplyMapper.createMeetReply(meetReplyDto);
    }

    @Override
    public List<MeetReplyDto> meetReplyList(int meetArticleId) {
        return meetReplyMapper.findByMeetReplyAll(meetArticleId);
    }

    @Override
    public int meetReplyCount(int meetArticleId) {
        return meetReplyMapper.meetReplyCount(meetArticleId);
    }

    @Override
    public void participate(int categoryId, int meetArticleId, String memberId) {
        meetArticleMapper.participate(categoryId, meetArticleId, memberId);
    }

    @Override
    public void cancelParticipation(int categoryId, int meetArticleId, String memberId) {
        meetArticleMapper.cancelParticipation(categoryId, meetArticleId, memberId);
    }

    @Override
    public MeetTagDto searchByMeetTagId(int meetTagId) {
        return meetTagMapper.findByMeetTagId(meetTagId);
    }

}
