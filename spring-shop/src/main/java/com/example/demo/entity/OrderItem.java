package com.example.demo.entity;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;



import lombok.*;


@Entity
@Table(name = "order_item")
@Getter
@Setter
@ToString
public class OrderItem extends BaseEntity{


		@Id
		@Column(name = "order_item_id")
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Long id;
		
		@JoinColumn(name = "order_id")
		@ManyToOne(fetch = FetchType.LAZY)
		private Order order;
		
		
		@JoinColumn(name = "item_id")
		@ManyToOne(fetch = FetchType.LAZY)  //지연로딩 설정
		private Item item;
		
		private int orderPrice;   //주문 가격
		
		private int count;    //주문 수량

		
		
	

	}

