package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.constance.Role;
import com.example.demo.dto.MemberFormDTO;
import com.example.demo.entity.Member;
import com.example.demo.service.MemberService;


@SpringBootTest
@AutoConfigureMockMvc    //MockUp, 실제 객체와 비슷하지만 테스트에 필요한 기능만 제공하는 가짜 객체를 만든다. -> 웹브라우저에서 요청을 하는 것처럼 작성 가능
@Transactional  //test 실행 후 rollback
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberControllerTest {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Member createMember(String email, String password) {
		MemberFormDTO member = new MemberFormDTO();
		member.setName("나야");
		member.setEmail(email);
		member.setAddress("여기삼");
		member.setPassword(password);
		
		Member member2=  Member.createMember(member, passwordEncoder);
		
		return memberService.saveMember(member2);
	}
	
	@Test
	@DisplayName("로그인 성공 테스트")	
	public void loginSuccessTest() throws Exception{
		this.createMember("test@test.com", "test1234");
		mockMvc.perform(formLogin().userParameter("email")
                .loginProcessingUrl("/member/login")   //로그인 url 지정 후 아래 유저id psw 던짐 
                .user("test@test.com").password("test1234")).andExpect(SecurityMockMvcResultMatchers.authenticated());   //그리고 실행, 보안 가상 객체 결과 비교.인증  로그인 인증 성공시 테스크 코드를 통과
	}
	
	
	@Test
	@DisplayName("로그인 실패 테스트")
	public void loginFailTest() throws Exception{
		this.createMember("test@test.com", "test1235");
		mockMvc.perform(formLogin().userParameter("email")
                .loginProcessingUrl("/member/login")   //로그인 url 지정 후 아래 유저id psw 던짐 
                .user("test@test.com").password("1234")).andExpect(SecurityMockMvcResultMatchers.unauthenticated());   //그리고 실행, 보안 가상 객체 결과 비교.인증  로그인 인증 성공시 테스크 코드를 통과
		
	}
	
	
	
 

}
