package com.cafe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.dto.StoreReservationDateDto;
import com.cafe.entity.StoreReservationDate;

public interface StoreReservationDateRepository extends JpaRepository<StoreReservationDate,Long>{

	List<StoreReservationDate> findByCafeId(Long cafeId);
	
}
