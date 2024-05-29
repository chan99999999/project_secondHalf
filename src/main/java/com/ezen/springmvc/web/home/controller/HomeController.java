package com.ezen.springmvc.web.home.controller;

import com.ezen.springmvc.domain.category.dto.CategoryDto;
import com.ezen.springmvc.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final CategoryService categoryService;

    @GetMapping("/")
    public String home(Model model){
<<<<<<< HEAD
        List<CategoryDto> categoryList = categoryService.findByCategoryList();
=======
        model.getAttribute("loginMember");
        List<CategoryDto> categoryList = categoryService.getCategoryList();
>>>>>>> 262a8bafb5ee46a312361735c4f630a06d5e1552
        model.addAttribute("categoryList", categoryList);
        return "index";
    }
}
