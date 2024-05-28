package com.ezen.springmvc.domain.meetArticle.service;

import com.ezen.springmvc.domain.meetArticle.dto.CategoryDto;
import com.ezen.springmvc.domain.meetArticle.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> findByCategoryAll(){
        return categoryMapper.findByCategoryAll();
    }

}
