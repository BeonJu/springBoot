package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.properties.PropertyMapping;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.example.constance.ItemSellStatus;
import com.example.demo.entity.Item;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {

	//의존성 주입
	@Autowired
	ItemRepository itemRepository;
	
//	@Test
//	@DisplayName("Test is Test")
//	public void createItemTest() {
//		Item item = new Item();
//		
//		item.setItemNm("Test Product");
//		item.setPrice(1000);
//		item.setItemDetail("Test is hard");
//		item.setItemSellStatus(ItemSellStatus.SELL);
//		item.setStockNumber(100);
//		item.setRegTime(LocalDateTime.now());
//		item.setUpdateTime(LocalDateTime.now());
//		
//		Item savedItem = itemRepository.save(item);
//		
//		System.out.println(savedItem.toString());
//		
//	}
	
	
	public void createItemTest() {
		for(int i=0; i<=10; i++) {
			Item item = new Item();
			
			item.setItemNm("Test Product"+i);
			item.setPrice(10000 + i);
			item.setItemDetail("Test is hard" + i);
			item.setItemSellStatus(ItemSellStatus.SELL);
			item.setStockNumber(100);
			item.setRegTime(LocalDateTime.now());
			item.setUpdateTime(LocalDateTime.now());
			
			Item savedItem = itemRepository.save(item);
			}
	
}
	@Test
	@DisplayName("상품명 조회 테스트")
	public void findByItemNmTest() {
	
		this.createItemTest();
//		List<Item> itemList = itemRepository.findByItemNm("Test Product3");
//		List<Item> itemList = itemRepository.findByItemNmOrItemDetail("Test Product3", "Test is hard5");
//		List<Item> itemList = itemRepository.findByPriceBetween(1001, 1003);
//		List<Item> itemList = itemRepository.findByPriceLessThan(1004);
		List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(1008);
		
		for(Item item : itemList) {
			
			System.out.println(item.toString());
			
		}
		
	}
	
	
	
	@Test
	@DisplayName("상품명 조회 문제1")
	public void findByItemNmQTest1() {
	
		this.createItemTest();
		List<Item> itemList = itemRepository.findByItemNmAndItemSellStatus("Test Product1", ItemSellStatus.SELL);
		for(Item item : itemList) {
			System.out.println(item.toString());
		}
		
	}
	
	@Test
	@DisplayName("상품명 조회 문제2")
	public void findByItemNmQTest2() {
	
		this.createItemTest();
		List<Item> itemList = itemRepository.findByPriceBetween(10004, 10008);
		for(Item item : itemList) {
			System.out.println(item.toString());
		}
		
	}
	
	
	@Test
	@DisplayName("상품명 조회 문제3")
	public void findByItemNmQTest3() {
	
		this.createItemTest();
		List<Item> itemList = itemRepository.findByRegTimeGreaterThan(LocalDateTime.of(2023, 1, 1, 12, 12, 44));
		for(Item item : itemList) {
			System.out.println(item.toString());
		}
		
	}
	
	
	@Test
	@DisplayName("상품명 조회 문제4")
	public void findByItemNmQTest4() {
	
		this.createItemTest();
		List<Item> itemList = itemRepository.findByItemSellStatusNotNull();
		for(Item item : itemList) {
			System.out.println(item.toString());
		}
		
	}
	
	@Test
	@DisplayName("상품명 조회 문제5")
	public void findByItemNmQTest5() {
	
		this.createItemTest();
		List<Item> itemList = itemRepository.findByItemDetailLike("%1");
		for(Item item : itemList) {
			System.out.println(item.toString());
		}
		
	}
	
	
 
 

}
