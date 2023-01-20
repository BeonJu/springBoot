package com.example.demo.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.dto.ItemSearchDTO;
import com.example.demo.entity.Item;

//사용자 정의 인터페이스
public interface ItemRepositoryCustom {
	Page<Item> getAdminItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable);
}
