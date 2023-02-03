package com.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class memberController {

	
	
	@GetMapping(value = "/member/login")
	public String memberLogin() {
		
		return "/member/memberLogin";
	}
	
	@GetMapping(value = "/member/signup")
	public String memberSignUp() {
		
		return "/member/memberSignUp";
	}
}
