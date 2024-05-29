package com.ezen.springmvc.web.member.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;
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
    @Length(min = 4, max = 10, message = "아이디는 {min}~{max}자 사이의 필수 입력 항목입니다.")
    @Pattern(regexp = "^[a-zA-Z][0-9a-zA-Z]{5,9}$", message = "아이디 형식이 유효하지 않습니다.")
    private String memberId;        /**회원 아이디*/
    private String memberAddress;   /**회원 주소*/
    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    private String name;            /**회원 이름*/
    @NotBlank(message = "닉네임(별명)은 필수 입력 항목입니다.")
    private String nickname;        /**회원 닉네임(별명)*/
    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "이메일 형식이 유효하지 않습니다.")
    private String email;           /**회원 이메일 주소*/
    private String birthDate;       /**회원 생년월일*/
    private String gender;          /**회원 성별*/
    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    private String memberPasswd;    /**회원 비밀번호*/
    @NotBlank(message = "비밀번호를 한번 더 입력해주세요.")
    private String rePasswd;        /**회원 비밀번호 재입력*/
    private String regdate;         /**회원 아이디 생성일자*/
}
