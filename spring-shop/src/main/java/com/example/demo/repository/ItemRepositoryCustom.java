package com.example.demo.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.dto.ItemSearchDTO;
import com.example.demo.dto.MainItemDTO;
import com.example.demo.entity.Item;

//사용자 정의 인터페이스
public interface ItemRepositoryCustom {
	//상품관리 페이지 아이템을 가져온다.
	Page<Item> getAdminItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable);
	
	//메인화면에 뿌려줄 아이템을 가져온다.
	Page<MainItemDTO> getMainItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable); 
}
