package com.ezen.springmvc.domain.placemap.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MapDto {
    private long placeId;         // DB의 장소번호 PK(시퀀스 자동생성)
    private long mapId;           // 카카오맵 API의 장소 id
    private String placeName;       // 장소명
    private String addressName;     // 지번주소
    private String roadAddressName; // 도로명주소
    private String phone;           // 전화번호
    private String y;               // y좌표(위도)
    private String x;               // x좌표(경도)
    private String categoryName;    // 카테고리 // DB에 저장X
    private String placeUrl;        // 카카오맵 장소 URL // DB에 저장X
}