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

import com.example.constance.OrderStatus;

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

}
