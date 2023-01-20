package com.example.demo.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.example.constance.ItemSellStatus;
import com.example.demo.dto.ItemFormDTO;

import lombok.*;

@Table(name = "Item")  //테이블 명 지정
@Entity
@Getter
@Setter
@ToString
public class Item extends BaseEntity{
	
//	not null이 아닐 시엔 객체 타입(integer, String)으로 지정 해야 됩니다. null이 디폴트값으로 생성 하지 않기 위해서
	@Id
	@Column(name = "item_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;  //상품 코드
	
	@Column(nullable = false, length = 50)
	private String itemNm;  // 상품명
	
	@Column(nullable = false, name="pirce")
	private int price;  //가격
	
	@Column(nullable = false)
	private int stockNumber;  //재고수량
	
	@Column(nullable = false)
	@Lob
	private String itemDetail; // 상품 상세설명
	
	@Column
	@Enumerated(EnumType.STRING)
	private ItemSellStatus itemSellStatus;  // 상품 판매상태
	
	
	
    //itemFormDTO 매개변수를 받아서 값 입력
	public void updateItem(ItemFormDTO itemFormDTO) {
		this.itemNm = itemFormDTO.getItemNm();
		this.price = itemFormDTO.getPrice();
		this.stockNumber = itemFormDTO.getStockNumber();
		this.itemDetail = itemFormDTO.getItemDetail();
		this.itemSellStatus = itemFormDTO.getItemSellStatus();
	}
	
	
	
}
