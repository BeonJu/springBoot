package com.example.demo.dto;

import javax.persistence.Column;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.Length;

import lombok.*;


@Getter
@Setter
public class MemberFormDTO {
//회원가입으로부터 넘어오는 가입정보를 담을 DTO
	
	//유효성 검사
	@NotBlank(message = "이름은 필수 입력 값입니다.")
	private String name;
	
	@NotEmpty(message = "이메일은 필수 입력 값입니다.")
	@Email(message = "이메일 형식으로 입력 해주세요.")
	private String email;
	
	@NotEmpty(message = "이메일은 필수 입력 값입니다.")
	@Length(min = 8, max = 16, message = "비밀번호는 8자 이상 16이하로 입력 해주세요.")
	private String password;
	
	@NotBlank(message = "주소는 필수 입력 값입니다.")
	private String address;
	
	
	
}
