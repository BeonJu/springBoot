package com.example.demo.entity;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.MemberFormDTO;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.MemberService;



@SpringBootTest
@Transactional  //test 실행 후 rollback
@TestPropertySource(locations = "classpath:application-test.properties")
class CartTest {

	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@PersistenceContext    //영속성 컨테스트를 사용하기 위한 선언
	EntityManager em;   // 엔티티 매니저
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Member createMember( ) {
		MemberFormDTO member = new MemberFormDTO();
		member.setName("나야두");
		member.setEmail("test@test.com");
		member.setAddress("한국");
		member.setPassword("1234");
		
		
		return Member.createMember(member, passwordEncoder);
	}
	
	@Test
	@DisplayName("장바구니 회원 엔티티 매핑 조회 테스트")
	public void findCartAndMemberTest() {
		Member member = createMember();   // 멤버 객채를 생성
		memberRepository.save(member);    // 영속성 컨테스트에 멤버 객체를 저장(insert, 동일한 객체가 있으면 update)
	
	Cart cart = new Cart();
	cart.setMember(member);
	cartRepository.save(cart);
	
	em.flush();  //트랜젝션이 끝낼 필요가 있을 때 commit 하여 DB에 반영
	
	em.clear();  //영속성 컨테스트에서 비영속으로 바꿈, 실제 DB에서 cart entity를 호출 시 회원(member) entity도 같이 가져 오는지 확인 차 클리어함 
	
	Cart saveCart = cartRepository.findById(cart.getId())
			.orElseThrow(EntityNotFoundException::new);   //옵셔널
	assertEquals(saveCart.getMember().getId(), member.getId()); // true 면 테스트 코드 통과, false 실패
	
	}
	
	
}
