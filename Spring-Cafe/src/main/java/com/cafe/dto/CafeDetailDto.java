package com.cafe.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CafeDetailDto {
	
	private Long id;
	
	private String businessName;
	
	private String address;
	
	private String phone;
	
	private String info;
	
	//대표 이미지 imgUrl
	private String imgUrlY;
	
	//대표가 아닌 이미지로 슬라이드에 사용 될 것 imgUrl
	private List<String> imgUrlN = new ArrayList<>();
	
	
}
