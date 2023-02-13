package com.cafe.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cafe.dto.CafeSearchDto;
import com.cafe.dto.MainCafeDto;
import com.cafe.entity.CafeImg;

//페이징을 위한 사용자 정의 인터페이스
public interface CafeRepositoryCustom {
	//카페 페이지 아이템을 가져온다.
	//Page<CafeRegister> getAdminItemPage(CafeSearchDto cafeSearchDto, Pageable pageable);
	
	//메인화면에 뿌리는 아이템을 가져온다.
	Page<MainCafeDto> getMainCafeRegisterPage(CafeSearchDto cafeSearchDto , Pageable pageable);

}
