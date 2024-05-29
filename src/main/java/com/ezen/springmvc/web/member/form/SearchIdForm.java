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
public class SearchIdForm {
    private String searchName;
    private String searchNickname;
}
