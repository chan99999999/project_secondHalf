package com.ezen.springmvc.domain.category.mapper;

import com.ezen.springmvc.domain.category.dto.CategoryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<CategoryDto> findByCategoryAll();
}
