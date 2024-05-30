package com.ezen.springmvc.web.member.validator;

import com.ezen.springmvc.web.member.form.MemberForm;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

//@Component
public class editMemberValidator implements Validator{
	@Override
	public boolean supports(Class<?> clazz) {
		//Class.isAssignableFrom() 메서드는 특정 Class가 어떤 클래스를 상속했는지
		// 또는 어떤 인터페이스를 구현했는지 체크하는 메서드이다.
		return MemberForm.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		MemberForm memberForm = (MemberForm) target;
		// 폼 필드 검증
		if (!StringUtils.hasText(memberForm.getMemberId())) {
			errors.rejectValue("id", "required");
		} else {
			if(memberForm.getMemberId().length() < 4 || memberForm.getMemberId().length() > 10){
				errors.rejectValue("id", "range", new Object[]{4, 10}, null);
			}
		}
		if(!StringUtils.hasText(memberForm.getName())){
			errors.rejectValue("name", "required");
		}
		if(!StringUtils.hasText(memberForm.getNickname())){
			errors.rejectValue("nickname", "required");
		}
		if(!StringUtils.hasText(memberForm.getMemberPasswd())){
			errors.rejectValue("passwd", "required");
		}
		if(!StringUtils.hasText(memberForm.getRePasswd())){
			errors.rejectValue("rePasswd", "required");
		}
		if(!StringUtils.hasText(memberForm.getEmail())){
			errors.rejectValue("email", "required");
		}
	}
}

