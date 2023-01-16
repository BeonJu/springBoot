package com.example.demo.dto;

import java.time.LocalDateTime;


import com.example.constance.ItemSellStatus;

import lombok.*;


@Getter
@Setter
public class ItemDTO {
	

		private Long id;  //상품 코드
		

		private String itemNm;  // 상품명
		

		private int price;  //가격
		

		private int stockNumber;  //재고수량

		
		private String itemDetail; // 상품 상세설명
		

		private ItemSellStatus itemSellStatus;  // 상품 판매상태
		

		private LocalDateTime regTime;  // 등록 시간

		
		private LocalDateTime updateTime; // 수정 시간
		
		
	}


