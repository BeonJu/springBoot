package com.cafe.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.*;


@Getter
@Setter
public class MainCafeDto {
	
	private Long id;
	
	private String business_name;
	
	private String address;
	
	private String imgUrl;

	
	@QueryProjection  //쿼리 dsl로 조회를 할 때 DTO를 바로 받음
	public MainCafeDto(Long id, String business_name, String address, String imgUrl) {
		this.id = id;
		this.business_name = business_name;
		this.address = address;
		this.imgUrl = imgUrl;
	}
}
