package com.cafe.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.entity.CafeImg;

public interface CafeImgRepository  extends JpaRepository<CafeImg, Long> {
	
	//CafeImg findByRepimgYnAndCafeId(Long cafe_id, String repimgYn);
}
