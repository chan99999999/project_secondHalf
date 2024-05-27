package com.ezen.springmvc.web.member.form;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
/**
 * 회원가입 폼에 대응하는 Form 클래스
 */
public class EditForm {
    private String nickname;
    private String email;
    private String hobby;
    private String interest;
    private String introduce;
}
