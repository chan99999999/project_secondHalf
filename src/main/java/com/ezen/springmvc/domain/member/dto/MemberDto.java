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
    private String memberAddress;
    private String memberPasswd;
    private String name;
    private String nickname;
    private String birthDate;
    private String gender;
    private String email;
    private String grade;
    private String hobby;
    private String interest;
    private String introduce;
    private String picture;
    private String storePicture; //프로필사진
    private String regdate;
}
