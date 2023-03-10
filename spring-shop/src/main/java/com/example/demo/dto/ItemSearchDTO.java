package com.example.demo.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.example.demo.constance.ItemSellStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSearchDTO {
	
	private String searchDateType;
	
	@Enumerated(EnumType.STRING)
	private ItemSellStatus searchSellStatus;
	
	
	private String searchBy;
	
	
	private String searchQuery ="";

}
