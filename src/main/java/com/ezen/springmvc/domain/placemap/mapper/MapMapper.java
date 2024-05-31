package com.ezen.springmvc.domain.placemap.mapper;

import com.ezen.springmvc.domain.placemap.dto.MapDto;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MapMapper {
    void createPlaceMap(MapDto mapDto);
}