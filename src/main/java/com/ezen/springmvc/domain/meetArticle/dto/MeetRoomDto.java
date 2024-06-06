package com.ezen.springmvc.domain.meetArticle.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MeetRoomDto {
    private int meetRoomId;
    private String joinMemberId;
    private String nickname;
}
