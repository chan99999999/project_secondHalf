package com.ezen.springmvc.domain.category.service;

import com.ezen.springmvc.domain.category.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    /**
     * 카테고리 목록 반환
     * @return 카테고리 목록
     */
    List<CategoryDto> getCategoryList();

}
