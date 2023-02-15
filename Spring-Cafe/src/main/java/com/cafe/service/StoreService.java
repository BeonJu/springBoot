package com.cafe.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafe.dto.StoreMenuDto;
import com.cafe.dto.StoreReservationDateDto;
import com.cafe.entity.StoreMenu;
import com.cafe.entity.StoreReservationDate;
import com.cafe.repository.StoreMenuRepository;
import com.cafe.repository.StoreReservationDateRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreService {
	
	private final StoreReservationDateRepository storeReservationDateRepository;
	private final StoreMenuRepository storeMenuRepository;
	
	
	public void regMenu(StoreMenuDto storeMenuDto) throws Exception {
		
		StoreMenu storeMenu = storeMenuDto.createMenu();
		storeMenuRepository.save(storeMenu);
		
	}
	
	public void regReservationDate(StoreReservationDateDto storeReservationDateDto) throws Exception {
		StoreReservationDate storeReservationDate = storeReservationDateDto.createDate();
		storeReservationDateRepository.save(storeReservationDate);
	}
	
    
	
	

}
