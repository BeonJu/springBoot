package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing  //jpa의 auding기능 활성화 어노테이션
public class AuditConfig {

	//싱글톤 패턴의 생성자
	@Bean
	public AuditorAware<String> auditorProvider(){
		return new AuditorAwarelmpl();
	}
	
}
