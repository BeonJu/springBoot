package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.service.MemberService;


@Configuration   //스프링에서 설정 클래스로 사용 지정
@EnableWebSecurity    // SpringSecurityFilterChain이 자동으로 포함됨  
public class SecurityConfig  {

	@Autowired
	MemberService memberService;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		//login setting
		http.formLogin()
		.loginPage("/member/login")      //login page url setting
		.defaultSuccessUrl("/")        // login success 시 이동할 페이지 setting
		.usernameParameter("email")    //로그인시 사용할 파라메터 이름
		.failureUrl("/member/login/error")  // 로그인 실패시 이동할 url
		.and()
		.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))    //로그아웃 페이지 url
		.logoutSuccessUrl("/");  //로그아웃 성공 시 페이지 이동 할 url
		
		return http.build(); 
	}
	
	//psw 암호화 인코딩을 위한 Bean(DTO)
	@Bean
	public PasswordEncoder passwordEncoder() {
		//
		
		return new BCryptPasswordEncoder();   //객채를 리턴하여 객채 생성
	}

	
	
}
