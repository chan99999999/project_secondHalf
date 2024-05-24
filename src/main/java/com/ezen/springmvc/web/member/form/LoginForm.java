package com.ezen.springmvc.web.member.form;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
/**
 * 로그인 폼에 대응하는 form 클래스
 */
public class LoginForm {
    private String loginId;
    private String loginPasswd;
    private boolean rememberLoginId;
}
