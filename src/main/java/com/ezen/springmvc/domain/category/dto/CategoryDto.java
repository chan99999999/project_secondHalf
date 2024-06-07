package com.ezen.springmvc.domain.category.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CategoryDto {
    private int categoryId;         /** 카테고리 번호 */
    private String categoryName;    /** 카테고리 이름 */
    private String adminId;         /** 관리자 아이디 */
}
