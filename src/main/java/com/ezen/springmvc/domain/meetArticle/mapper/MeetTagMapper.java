package com.ezen.springmvc.domain.meetArticle.mapper;

import com.ezen.springmvc.domain.meetArticle.dto.MeetTagDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MeetTagMapper {
    void createMeetTag(MeetTagDto meetTagDto);
    MeetTagDto findByMeetTagId(int meetTagId);
    List<MeetTagDto> findByAllMeetTagName(String tagName);
}