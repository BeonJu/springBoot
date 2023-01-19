package com.example.demo.entity;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.repository.MemberRepository;



@SpringBootTest
@Transactional  //test 실행 후 rollback
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberTest {

@Autowired
MemberRepository memberRepository;

@PersistenceContext
EntityManager em;

@Test
@DisplayName("auditing 테스트")
@WithMockUser(username = "Tester", roles="USER")
public void auditingTest() {
	Member newMember = new Member();
	memberRepository.save(newMember);
	
	em.flush();
	em.clear();
	
	Member member = memberRepository.findById(newMember.getId())
			.orElseThrow(EntityNotFoundException::new);
	
	System.out.println("regTime: "+ member.getRegTime());
	System.out.println("upDateTime: "+ member.getUpDateTime());
	System.out.println("regUser: "+ member.getCreateBy());
	System.out.println("upDateUser: "+ member.getModifiedBy());
	
	
	
}

}
