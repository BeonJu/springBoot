package com.example.demo.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.CoderResult;
import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.example.constance.ItemSellStatus;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;


@SpringBootTest
@Transactional  //test 실행 후 rollback
@TestPropertySource(locations = "classpath:application-test.properties")
class OrderTest {

 

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	ItemRepository itemRepository;
	
	@PersistenceContext    //영속성 컨테스트를 사용하기 위한 선언
	EntityManager em;   // 엔티티 매니저
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	OrderItemRepository orderItemRepository;
	

	public Item createItemTest() {
		
			Item item = new Item();
			
			item.setItemNm("Test Product");
			item.setPrice(10000);
			item.setItemDetail("Test is hard");
			item.setItemSellStatus(ItemSellStatus.SELL);
			item.setStockNumber(100);
			item.setRegTime(LocalDateTime.now());
//			item.setUpdateTime(LocalDateTime.now());
			
			
			
		return item;
	
}
	
	@Test
	@DisplayName("장바구니 회원 엔티티 매핑 조회 테스트")
	public void cascadeTest() {
		Order order = new Order();
		for(int i=0; i<3; i++) {
			Item item = this.createItemTest();    //물건 객체 3개 생성
			itemRepository.save(item);
			
			OrderItem orderItem = new OrderItem();
			orderItem.setItem(item);
			orderItem.setCount(10);
			orderItem.setOrderPrice(10000);
			orderItem.setOrder(order);
			
			order.getOrderItems().add(orderItem);
		}
		orderRepository.saveAndFlush(order);
		em.clear();
		
		Order saveOrder = orderRepository.findById(order.getId())
				.orElseThrow(EntityNotFoundException::new);
		
		assertEquals(3, saveOrder.getOrderItems().size());

	}
	
	
	
	public Order createOrder() {
		Order order = new Order();
		for(int i=0; i<3; i++) {
			Item item = this.createItemTest();    //물건 객체 3개 생성
			itemRepository.save(item);
			
			OrderItem orderItem = new OrderItem();
			orderItem.setItem(item);
			orderItem.setCount(10);
			orderItem.setOrderPrice(10000);
			orderItem.setOrder(order);
			
			order.getOrderItems().add(orderItem);
		}
		Member member = new Member();     //멤버 객채는 order과 종속성이 있어서 이다
		memberRepository.save(member);
		
		order.setMember(member);
		orderRepository.save(order);
		
		return order;
		
	}
	
	
	@Test
	@DisplayName("고아객체 제거 테스트")
	public void orphanRemovalTest() {
		
		Order order = this.createOrder();
		order.getOrderItems().remove(0);  //주문 엔티티에서 주문상품 엔티티를 삭제 했을 때 orderItem 엔티니가 삭제 된다  
		em.flush();
	}
	
	
	@Test
	@DisplayName("지연 로딩  테스트")
	public void lazyLoadingTest() {
		
		Order order = this.createOrder();
		Long orderItemId = order.getOrderItems().get(0).getId();
		
		em.flush();
		em.clear();
		
		OrderItem orderItem = orderItemRepository.findById(orderItemId)
				.orElseThrow(EntityNotFoundException::new);
	}
	
	
}
