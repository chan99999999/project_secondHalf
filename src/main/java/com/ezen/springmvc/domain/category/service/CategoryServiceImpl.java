package com.ezen.springmvc.domain.category.service;

import com.ezen.springmvc.domain.category.dto.CategoryDto;
import com.ezen.springmvc.domain.category.mapper.CategoryMapper;
import com.ezen.springmvc.domain.dailyarticle.mapper.DailyArticleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    /**
     * 카테고리 목록 반환 구현
     * @return 카테고리 목록
     */
    @Override
    public List<CategoryDto> getCategoryList() {
        return categoryMapper.findByCategoryAll();
    }
}
