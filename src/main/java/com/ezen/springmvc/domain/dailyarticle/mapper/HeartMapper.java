package com.ezen.springmvc.domain.dailyarticle.mapper;

import com.ezen.springmvc.domain.dailyarticle.dto.HeartDto;
import com.ezen.springmvc.domain.dailyarticle.dto.ReplyDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HeartMapper {

    /**
     * 좋아요 등록
     * @param heartDto 좋아요
     */
    public void createHeart(HeartDto heartDto);

}
