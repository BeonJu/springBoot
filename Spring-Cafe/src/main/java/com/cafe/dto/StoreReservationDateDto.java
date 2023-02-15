package com.cafe.dto;

import org.modelmapper.ModelMapper;


import com.cafe.entity.StoreReservationDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreReservationDateDto {

	private Long id;
	
	private String date;
	
	private String time;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public StoreReservationDate createDate() {
		return modelMapper.map(this,StoreReservationDate.class);
	}
	
	public static StoreReservationDateDto of(StoreReservationDate storeReservationDate) {
		return modelMapper.map(storeReservationDate, StoreReservationDateDto.class);
		
	}
	
}
