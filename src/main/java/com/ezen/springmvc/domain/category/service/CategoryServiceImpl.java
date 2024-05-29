package com.ezen.springmvc.domain.category.service;

import com.ezen.springmvc.domain.category.dto.CategoryDto;
import com.ezen.springmvc.domain.category.mapper.CategoryMapper;
<<<<<<< HEAD
=======
import com.ezen.springmvc.domain.dailyarticle.mapper.DailyArticleMapper;
>>>>>>> 262a8bafb5ee46a312361735c4f630a06d5e1552
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
<<<<<<< HEAD
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> findByCategoryList(){
        return categoryMapper.findByCategoryAll();
    }

=======

    private final CategoryMapper categoryMapper;


    /**
     * 카테고리 목록 반환 구현
     *
     * @return 카테고리 목록
     */
    @Override
    public List<CategoryDto> getCategoryList() {
        return categoryMapper.findByCategoryAll();
    }
>>>>>>> 262a8bafb5ee46a312361735c4f630a06d5e1552
}
