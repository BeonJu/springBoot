package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;




@SpringBootTest
@AutoConfigureMockMvc    //MockUp, 실제 객체와 비슷하지만 테스트에 필요한 기능만 제공하는 가짜 객체를 만든다. -> 웹브라우저에서 요청을 하는 것처럼 작성 가능
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Test
	@DisplayName("상품 등록 관리자 페이지 권한 테스트")
	@WithMockUser(username="admin", roles= "ADMIN")
	public void itemAdminFormTest()  throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/new"))   //get request "/admin/item/new"
		.andDo(print())     //request for response console print Msg
		.andExpect(status().isOk());   // 상태 코드가 200 이면 테스크 코드 통과
		
		
	}
	
	
	@Test
	@DisplayName("상품 등록 일반 유저 페이지 권한 테스트")
	@WithMockUser(username="admin", roles= "USER")
	public void itemUserFormTest()  throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/new"))   //get request "/admin/item/new"
		.andDo(print())     //request for response console print Msg
		.andExpect(status().isForbidden());   // 상태 코드가 403 이면 테스크 코드 통과
		
		
	}

}
