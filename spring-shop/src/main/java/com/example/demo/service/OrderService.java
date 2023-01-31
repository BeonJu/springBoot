package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.codehaus.groovy.util.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.OrderHistDTO;
import com.example.demo.dto.OrderItemDTO;
import com.example.demo.entity.Item;
import com.example.demo.entity.ItemImg;
import com.example.demo.entity.Member;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.repository.ItemImgRepository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service //service 클래스의 역할
@Transactional //서비스 클래서에서 로직을 처리하다가 에러가 발생하면 로직을 수행하기 이전 상태로 되돌려 준다. 
@RequiredArgsConstructor  // final영속성 
public class OrderService {

	private final ItemRepository itemRepository;
	private final MemberRepository memberRepository;
	private final OrderRepository orderRepositorty;
	private final ItemImgRepository itemImgRepository;
	
	public Long order(OrderDTO orderDto, String email) {
		Item item = itemRepository.findById(orderDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);
		
		Member member = memberRepository.findByEmail(email);
		
		List<OrderItem> orderItemList = new ArrayList<>(); 
		OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
		orderItemList.add(orderItem);
		
		Order order = Order.createOrder(member, orderItemList);
		orderRepositorty.save(order);
		
		return order.getId();
	}
	
	
	//읽어오기 전용
	@Transactional(readOnly = true)
	public Page<OrderHistDTO> getOrderList(String email, Pageable pageable){
		//주문 목록
		List<Order> orders = orderRepositorty.findOrders(email, pageable);
		
		//주문 갯수, 페이징 처리를 위한 값
		Long totalCount = orderRepositorty.countOrder(email);
		
		
		List<OrderHistDTO> orderHistDTOs = new ArrayList<>();
		
		for(Order order : orders) {
			OrderHistDTO orderHistDTO  = new OrderHistDTO(order);
			List<OrderItem> orderItems = order.getOrderItems();
			
			for(OrderItem orderItem : orderItems) {
				ItemImg  itemImg  = itemImgRepository.findByItemIdAndRepimgYn(orderItem.getItem().getId(), "Y");
				OrderItemDTO orderItemDTO = new OrderItemDTO(orderItem, itemImg.getImgUrl());
				orderHistDTO.addOrderItemDto(orderItemDTO);
			}
			orderHistDTOs.add(orderHistDTO);
		}
		return new PageImpl<OrderHistDTO>(orderHistDTOs, pageable, totalCount);
	}
	
	//현재 로그인한 사용자와 주문데이터를 생성한 사용자가 같은지 검사
	@Transactional(readOnly = true)
	public boolean validateOrder(Long orderId, String email) {
		Member curMember = memberRepository.findByEmail(email);  //로그인 사용자 찾기
		Order order = orderRepositorty.findById(orderId).orElseThrow(EntityNotFoundException::new); ////주문 번호 가져오기
		Member savedMember = order.getMember();  //주문 사용자 찾기
		
		if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())) {  
			return false;
		}
		return true;
	}
	
	//주문 취소 하는 메소드
	public void cancelOrder(Long orderId) {
		
		Order order = orderRepositorty.findById(orderId).orElseThrow(EntityNotFoundException::new);
		
		order.cancelOrder();  // 주문 취소 됬으니 재고 증가 메소드 실행
		
	}
	
	
	//주문 삭제 하는 메소드
	public void deleteOrder(Long orderId) {
		Order order = orderRepositorty.findById(orderId).orElseThrow(EntityNotFoundException::new);
		orderRepositorty.delete(order);
	}
	
	
	
	
}
