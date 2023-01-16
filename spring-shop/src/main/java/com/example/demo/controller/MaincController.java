package com.example.demo.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller //컨트롤러의 역할을 하는 클래스를 정의
@RequestMapping(value = "/") //request url 경로지정
public class MaincController {
	
	@GetMapping(value = "/")
	public String main() {
	
		return "Main";
	}
	

}
