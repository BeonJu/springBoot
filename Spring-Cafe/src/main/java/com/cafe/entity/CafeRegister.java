package com.cafe.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;


import com.cafe.dto.CafeRegisterDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Table(name = "Cafe")  //테이블 명 지정
@Entity
@Getter
@Setter
@ToString
public class CafeRegister {

	
	@Id
	@Column(name = "cafe_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;   //카페 코드 

	@Column(nullable = false, length = 100)
	private String businessName;   //  상호명
	
	@Column(nullable = false, length = 50)
	private String ownerName;   // 점주 성함

	@Column(nullable = false, length = 50)
	private String phone;   // 연락처
	
	@Column(nullable = false)
	private String address;  //카페 주소

	@Lob
	private String info;   // 카페 상세 개요
	
	
	// 카페 등록 페이지에서 넘어온 CafeRegisterDto 객채를 매개변수로 받아서 엔티티 값 설정
	public void updateCafe(CafeRegisterDto cafeRegisterDto) {
		this.businessName = cafeRegisterDto.getBusinessName();
		this.ownerName = cafeRegisterDto.getOwnerName();
		this.phone = cafeRegisterDto.getPhone();
		this.address = cafeRegisterDto.getAddress();
		this.info = cafeRegisterDto.getInfo();
	}
	
	
	
}
