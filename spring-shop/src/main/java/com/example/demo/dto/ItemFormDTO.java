package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import com.example.constance.ItemSellStatus;
import com.example.demo.entity.BaseEntity;
import com.example.demo.entity.Item;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ItemFormDTO extends BaseEntity{

	@Id
	@Column
	private Long id;  //상품 코드
	
	@NotBlank(message = "상품명은 필수 값입니다.")
	private String itemNm;  // 상품명
	
	@NotNull(message = "가격은 필수 값입니다.")
	private int price;	  //가격
	
	@NotNull(message = "상품명은 필수 값입니다.")
	private int stockNumber;  //재고수량

	@NotBlank(message = "상품 상세 설명은 필수 값입니다.")
	private String itemDetail; // 상품 상세설명
	

	private ItemSellStatus itemSellStatus;  // 상품 판매상태
	
	private List<ItemImgDTD> itemImgDTOList = new ArrayList<>();  //상품의 이미지 정보를 저장하는 리스트, 하나의 상품에 이미지는 다수 이기 때문에 사용
	
	private List<Long> itemImgIds = new ArrayList<>();   //상품 이미지 id를 저장 , 수정 시에 이미지 id를 담아주는 용도(타겟팅)
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public Item createItem() {
		return modelMapper.map(this,Item.class);
	}
	
	public static ItemFormDTO of(Item item) {
		return modelMapper.map(item,ItemFormDTO.class);
	}
	
	
	
	
}
