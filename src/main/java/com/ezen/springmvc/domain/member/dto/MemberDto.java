package com.ezen.springmvc.domain.member.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MemberDto {
    private String memberId;
    private String password;
    private String name;
    private String email;
    private String regdate;
}
