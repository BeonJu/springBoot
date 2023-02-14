package com.cafe.dto;




import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreListDto {
	
	private Long id;


	private String businessName;
	

	private String ownerName;


	private String phone;


	private String address;

	
	@QueryProjection  //쿼리 dsl로 조회를 할 때 DTO를 바로 받음
	public StoreListDto(Long id, String businessName, String address, String phone, String ownerName) {
		this.id = id;
		this.businessName = businessName;
		this.address = address;
		this.phone = phone;
		this.ownerName = ownerName;

	}
}
