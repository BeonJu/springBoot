package com.cafe.dto;

import org.modelmapper.ModelMapper;

import com.cafe.entity.StoreMenu;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreMenuDto {

	private Long id;
	
	private String menuNm;
	
	private int price;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public StoreMenu createMenu() {
		return modelMapper.map(this,StoreMenu.class);
	}
	
	public static StoreMenuDto of(StoreMenu storeMenu) {
		return modelMapper.map(storeMenu, StoreMenuDto.class);
	}

}
