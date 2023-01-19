package com.example.demo.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.MemberFormDTO;
import com.example.demo.entity.Member;
import com.example.demo.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller //컨트롤러의 역할을 하는 클래스를 정의
@RequestMapping(value = "/member") //request url 경로지정
@RequiredArgsConstructor  // final 의존성 주입  객체 생성
public class MemberController {
	
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
		

	//회원가입 화면
	@GetMapping(value = "/new")
	public String memberForm(Model model) {
		model.addAttribute("memberFormDTO", new MemberFormDTO());
		
		return "member/MemberForm";
	}
	
	//회원가입 버튼을 눌렀을때 실행 되는 메소드
	//@Valid 검증 어노테이션, 검증이 필요한 객체 앞에 붙여 주면 됨
	//BindingResult 위 Valid 검증 후에 결과 값을 넣는 객체
	@PostMapping(value = "/new")
	public String memberForm(@Valid MemberFormDTO memberFormDTO, BindingResult bindingResult, Model model) {
		//@Valid : 유효성을 검증하려는 객체 앞에 붙인다.
		//bindingResult: 유효성 검증후에 결과를 넣어준다.
		
		if(bindingResult.hasErrors()) {
			return "member/MemberForm";
		}
		try {
			Member member = Member.createMember(memberFormDTO, passwordEncoder);
			memberService.saveMember(member);
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage",e.getMessage());
			return "member/MemberForm";
		}

		
		return "redirect:/";    // 객체를 안가져감
	}
	
	@GetMapping(value = "/login")
	public String loginMember() {
		return "member/MemberLoginForm";
	}
	
	
	private final SessionManager sessionManager;
		
	
	/*
	@PostMapping(value = "/login2")
	public String loginMember2(HttpServletResponse response, HttpSession session, @RequestParam String email) {
		System.out.println("email: " + email);
		Cookie idCookie = new Cookie("userCookieId2", email);
		response.addCookie(idCookie);
		
		session.setAttribute("useSessionId2", email);
		
		sessionManager.createSession(email, response);
		
		return "member/memberLoginForm";
	}
	*/
	
	@GetMapping(value = "/login/error")
	public String loginError(Model model) {
		model.addAttribute("loginErrorMsg","아이디 또는 비밀번호를 확인해주세요");
		
		return "member/MemberLoginForm";
	}
	
}
