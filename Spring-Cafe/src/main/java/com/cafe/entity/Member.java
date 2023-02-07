package com.cafe.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cafe.constance.Role;
import com.cafe.dto.MemberDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
public class Member {
	
	@Id
	@Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	private String name;
	
	//중복 불가
	@Column(unique = true)
	private String email;
	

	private String password;
	

	private String phone;
	

	private String address;

	@Enumerated(EnumType.STRING)
	private Role role;
	
	
	public static Member createMember(MemberDto memberDto, PasswordEncoder passwordEncoder) {
		//member 엔티티 객체 생성
		Member member = new Member();
		//회원가입 page에서 dto 객채로 넘겨준 값을 member 엔티티에 매칭 및 바인딩
		member.setName(memberDto.getName());
		member.setEmail(memberDto.getEmail());
		member.setPhone(memberDto.getPhone());
		member.setAddress(memberDto.getAddress());
		
		//회원가입 page에서 dto 객채 중 password 값을 passwordEncoder의 encode 메소드에 인자 값으로 던지고 암호화 된 값을 member 엔티티에 매칭 및 바인딩 
		String password = passwordEncoder.encode(memberDto.getPassword());
		member.setPassword(password);

		//기본적으로 회원가입 하는 고객은 USER 역활이 Default 값이다
		member.setRole(Role.USER);
		
		return member;
		
	}
	
	
	
}
