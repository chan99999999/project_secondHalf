package com.ezen.springmvc.domain.category.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CategoryDto {
    private int categoryId;
    private String categoryName;
    private String adminId;
}
