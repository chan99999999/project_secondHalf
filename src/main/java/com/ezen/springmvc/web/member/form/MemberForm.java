package com.ezen.springmvc.web.member.form;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
/**
 * 회원가입 폼에 대응하는 Form 클래스
 */
public class MemberForm {
    private String memberId;
    private String memberAddress;
    private String name;
    private String nickname;
    private String email;
    private String birthDate;
    private String gender;
    private String memberPasswd;
    private String rePasswd;
    private String regdate;
}
