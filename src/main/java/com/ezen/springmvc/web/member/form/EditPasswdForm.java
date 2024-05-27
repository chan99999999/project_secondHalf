package com.ezen.springmvc.web.member.form;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class EditPasswdForm {
    private String oldPasswd;
    private String newPasswd;
    private String confirmPasswd;
}
