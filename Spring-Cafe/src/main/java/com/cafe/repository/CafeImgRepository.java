package com.cafe.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cafe.entity.CafeImg;


public interface CafeImgRepository  extends JpaRepository<CafeImg, Long> {
	
	//List<CafeImg> findByCafeId(Long cafeId);
	
//	@Query("SELECT c FROM CafeImg c where c.cafe_id = ?1")
//	List<CafeImg> findByCi(Long cafeId); 
	
	
	List<CafeImg> findByCafeIdOrderByIdAsc(Long cafeId);
	
	//상품의 대표 이미지를 찾음
	CafeImg findByCafeIdAndRepimgYn(Long cafeId, String repimgYn);
}
