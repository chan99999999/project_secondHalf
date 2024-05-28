package com.ezen.springmvc.domain.meetArticle.service;

import com.ezen.springmvc.domain.meetArticle.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    //    카테고리 목록
    List<CategoryDto> findByCategoryAll();
}
