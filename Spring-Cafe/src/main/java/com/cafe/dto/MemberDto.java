package com.cafe.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
	//회원 가입으로부터 넘어오는 정보를 담을 DTO
	
	//유효성 검사
	@NotBlank(message = "이름은 필수 입력 값입니다.")
	private String name;
	
	@NotEmpty(message = "이메일은 필수 입력 값입니다.")
	@Email(message = "이메일 형식으로 입력 해주세요.")
	private String email;
	
	@NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
	@Length(min = 8, max = 16, message = "비밀번호는 8자 이상 16이하로 입력 해주세요.")
	private String password;
	
	@NotEmpty(message = "비밀번호 재확인은 필수 입력 값입니다.")
	@Length(min = 8, max = 16, message = "비밀번호는 8자 이상 16이하로 입력 해주세요.")
	private String passwordAgain;
	
	@NotBlank(message = "연락처는 필수 입력 값입니다.")
	private String phone;
	
	@NotBlank(message = "주소는 필수 입력 값입니다.")
	private String address;

}
