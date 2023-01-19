package com.example.demo.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

//로그인한 사용자 정보를 등록자와 수정자로 지정
public class AuditorAwarelmpl implements AuditorAware<String> {

	
	@Override
	public Optional<String> getCurrentAuditor() {
		//현재 로그인한 사용자의 정보 호출
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String userId = "";
		
		if(authentication != null) {
			//사용자 이름 호출 
			userId = authentication.getName();
		}
		//사용자의 이름을 등록자와 수정자로 지정
		return Optional.of(userId); 
	}

	
	
}
