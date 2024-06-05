package com.ezen.springmvc.web.member.form;

import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.validator.constraints.Length;

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
    @Email(message = "이메일 형식이 유효하지 않습니다.")
    private String email;
    @Length(max = 50, message = "아이디는 {max}자까지만 입력 가능합니다.")
    private String hobby;
    @Length(max = 50, message = "아이디는 {max}자까지만 입력 가능합니다.")
    private String interest;
    @Length(max = 200, message = "아이디는 {max}자까지만 입력 가능합니다.")
    private String introduce;
}
