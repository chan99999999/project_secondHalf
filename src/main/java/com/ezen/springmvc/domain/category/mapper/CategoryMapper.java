package com.ezen.springmvc.domain.category.mapper;

import com.ezen.springmvc.domain.category.dto.CategoryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
<<<<<<< HEAD
    List<CategoryDto> findByCategoryAll();
=======

    /**
     * 카테고리 목록 반환
     * @return 카테고리 목록
     */
    public List<CategoryDto> findByCategoryAll();
>>>>>>> 262a8bafb5ee46a312361735c4f630a06d5e1552
}
