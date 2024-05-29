package com.ezen.springmvc.domain.meetArticle.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class TagDto {
    private int tagId;
    private String tagName;
}