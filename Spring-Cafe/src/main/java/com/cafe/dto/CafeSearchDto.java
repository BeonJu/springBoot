package com.cafe.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CafeSearchDto {

	private String searchDateType;
	
	private String searchBy;
	
	private String searchQuery = "";
	
}
