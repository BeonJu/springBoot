package com.example.demo.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



//업로드한 파일을 읽어올 경로를 설정
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {  
	@Value("${uploadpath}")   //프로퍼티의 uploadpath 값 호출
	String uploadPath;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		///images/** 모든 이미지들은  uploadPath 여기에서 찾아옴 주소값은 프로퍼티에서 확인
		registry.addResourceHandler("/images/**").addResourceLocations(uploadPath);
		
		
	}
	
	

}
