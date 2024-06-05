package com.ezen.springmvc.domain.review.dto;
import com.ezen.springmvc.domain.placemap.dto.MapDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class ReviewRequest {
    private String content;
    private MapDto mapDto;
}
