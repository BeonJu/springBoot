package com.cafe.controller;

import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cafe.dto.MemberDto;
import com.cafe.entity.Member;
import com.cafe.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value = "/member")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	
	
	@GetMapping(value = "/login")
	public String memberLogin() {
		return "/member/memberLogin";
	}
	
	@GetMapping(value = "/signup")
	public String memberSignUp(Model model) {
		//빈 memberDTO 객체를 생성하여 모델에 바인딩하여 회원가입 페이지로 던짐
		model.addAttribute("MemberDto", new MemberDto());
		
		return "/member/memberSignUp";
	}
	
	@PostMapping(value = "/signup")
	public String memberSignup(@Valid MemberDto memberDto, BindingResult bindingResult, Model model) {
		//@Valid : 유효성을 검증하려는 객체 앞에 붙인다.
		//bindingResult: 유효성 검증후에 결과를 넣어준다.
		
		if(bindingResult.hasErrors()) {
			return "/member/signup";
		}
		try {
			Member member = Member.createMember(memberDto, passwordEncoder);
			memberService.saveMember(member);
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage",e.getMessage());
			return "/member/signup";
		}
		return "redirect:/";    // 객체를 안가져감
	}
	
	
	@GetMapping(value = "/login/error")
	public String loginError(Model model) {
		model.addAttribute("loginErrorMsg","아이디 또는 비밀번호를 확인해주세요");
		
		return "member/login";
	}
}
