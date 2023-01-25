package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.constance.Role;
import com.example.demo.dto.MemberFormDTO;
import com.example.demo.entity.Member;



@SpringBootTest
@Transactional  //test 실행 후 rollback
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberServiceTest {

	@Autowired   //의존성 주입(자동)
	MemberService memberService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public Member createMember() {
		MemberFormDTO member = new MemberFormDTO();
		member.setName("나야");
		member.setEmail("adf@sdaf.com");
		member.setAddress("여기삼");
		member.setPassword("123456");
		
		return Member.createMember(member, passwordEncoder);
		
	}

	@Test  
	@DisplayName("회원가입 테스트")
	public void saveMemberTest() {
		Member member = createMember();
		
		Member saveMember = memberService.saveMember(member);
		
		//저장 이전 이후 의 data 비교 (data 무결성)
		assertEquals(member.getName(), saveMember.getName());
		assertEquals(member.getEmail(), saveMember.getEmail());
		assertEquals(member.getAddress(), saveMember.getAddress());
		assertEquals(member.getPassword(), saveMember.getPassword());
		assertEquals(member.getRole(), saveMember.getRole());
	}
	
	
	@Test  
	@DisplayName("중복 회원가입 테스트")
	public void saveDuplicationMemberTest() {
		Member member1 = createMember();
		Member member2 = createMember();
		
		memberService.saveMember(member1);
		
		//예외 처리 테스트
		Throwable e = assertThrows(IllegalStateException.class, () -> {
			memberService.saveMember(member2);
		});
		
		//저장 이전 이후 의 data 비교 (data 무결성)
		assertEquals("이미 가입된 회원입니다.",e.getMessage());
	}
	
}
