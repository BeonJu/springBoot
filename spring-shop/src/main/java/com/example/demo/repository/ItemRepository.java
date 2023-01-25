package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.example.demo.constance.ItemSellStatus;
import com.example.demo.dto.MainItemDTO;
import com.example.demo.entity.Item;


//CRUD , Paging 처리 를 위한 메소스 정의
//QuerydslPredicateExecutor QueryDSL 사용하기 위한놈
public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item>, ItemRepositoryCustom{

	List<Item> findByItemNm(String itemNm);
	// select * from Item where item_Nm = ?
	
	List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);
	
	List<Item> findByPriceBetween(int price, int price2);
	
	List<Item> findByRegTimeGreaterThan(LocalDateTime regTiem);
	
	List<Item> findByPriceLessThan(int price);
	
	List<Item> findByPriceLessThanOrderByPriceDesc(int price);
	
	List<Item> findByItemNmAndItemSellStatus(String itemNm, ItemSellStatus itemSellStatus);
	
	List<Item> findByItemSellStatusNotNull();
	
	List<Item> findByItemDetailLike(String itemDetail);


}
