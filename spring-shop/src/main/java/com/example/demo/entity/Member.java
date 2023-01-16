package com.example.demo.entity;



import javax.persistence.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.constance.Role;
import com.example.demo.dto.MemberFormDTO;

import lombok.*;


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
	
	@Column
	private String name;
	
	@Column(unique = true)
	private String email;
	
	@Column
	private String password;
	
	@Column
	private String address;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Role role;
	
	
	public static Member createMember(MemberFormDTO memberFromDTO, PasswordEncoder passwordEncoder) {
		Member member = new Member();
		member.setName(memberFromDTO.getName());
		member.setEmail(memberFromDTO.getEmail());
		member.setAddress(memberFromDTO.getAddress());
		
		String password =  passwordEncoder.encode(memberFromDTO.getPassword());   // psw crypt encoding
		member.setPassword(password);
		
		member.setRole(Role.USER);
		return member;
		
	}
	
	
	
	
	
	
}
