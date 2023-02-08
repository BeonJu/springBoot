package com.cafe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.entity.CafeImg;
import com.cafe.entity.CafeRegister;

public interface CafeImgRepository  extends JpaRepository<CafeImg, Long> {
	
//	List<CafeImg> findByCafeIdOrderByAsc(Long cafeId);
//	
//	CafeImg findByCafeIdAndRepimgYn(Long cafeId, String repimgYn);
}
