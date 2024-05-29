package com.ezen.springmvc.domain.category.mapper;

import com.ezen.springmvc.domain.category.dto.CategoryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    /**
     * 카테고리 목록 반환
     * @return 카테고리 목록
     */
    public List<CategoryDto> findByCategoryAll();
}
