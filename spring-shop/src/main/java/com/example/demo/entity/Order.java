package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.demo.constance.OrderStatus;

import lombok.*;


@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
public class Order extends BaseEntity{
	
	@Id
	@Column(name = "order_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@JoinColumn(name = "member_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;
	
	private LocalDateTime orderDate; //주문일
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;   //주문 상태
	
	@OneToMany(mappedBy = "order" , cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)  //orderitem의 컬럼인 order에 의해 관리됨, 양방향으로 만들고 외래키의 관리를 한다는 의미
	private List<OrderItem> orderItems = new ArrayList<>();

	
	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
		
		orderItem.setOrder(this);
		//양방향 관계 이기 때문에 order 와 orderItem에 서로 셋팅 해줘야 된다.
	}
	//order 객체를 생성한다 하나의 주문에 item은 여러개 들어 갈수있다.
	public static Order createOrder(Member member, List<OrderItem> orderItemsList) {
		Order order = new Order();
		order.setMember(member);
		
		for(OrderItem orderItem : orderItemsList) {
			order.addOrderItem(orderItem);
			
		}
		order.setOrderStatus(OrderStatus.ORDER);
		order.setOrderDate(LocalDateTime.now());
		
		
		return order;
		
	}
	
	//총 주문금액
	public int getTotalPrice() {
		int totalPrice = 0;
		for(OrderItem orderItem : orderItems) {
			totalPrice += orderItem.getTotalPrice();
		}
		return totalPrice;
	}
	
	//주문 취소
	public void cancelOrder() {
		this.orderStatus = OrderStatus.CANCLE;
		
		for(OrderItem orderItem : orderItems) {
			orderItem.cancel();  //재고 증가
			
		}
		
	}
	
	
	
}
