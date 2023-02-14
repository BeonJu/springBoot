package com.cafe.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Table(name = "Menu")  //테이블 명 지정
@Entity
@Getter
@Setter
public class StoreMenu {

	@Id
	@Column(name = "menu_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String menuNm;
	
	private int price;
	
	@ManyToOne
	@JoinColumn(name = "cafe_id")
	private CafeRegister cafe;
	
	
	
}
