package com.cafe.dto;



import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreDetailDto {

	private Long id;
	
	private String businessName;

	private String ownerName;

	private String phone;

	private String address;

	
	private List<StoreReservationDateDto> reservationDate = new ArrayList<>();
	
	private List<StoreMenuDto> menus = new ArrayList<>();
	
}
