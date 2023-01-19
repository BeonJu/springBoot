package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.constance.ItemSellStatus;
import com.example.demo.dto.ItemDTO;
import com.example.demo.entity.Item;

@Controller //컨트롤러의 역할을 하는 클래스를 정의
@RequestMapping(value = "/thymeleaf") //request url 경로지정
public class ThymeleafExController {
	
	@GetMapping(value = "/ex01")
	public String thymeleafEx01(Model model) {
		model.addAttribute("data", "타임리프 예제 입니다.");
		return "thymeleafEx/thymeleafEx01";
	}
	
	@GetMapping(value = "/ex02")
	public String thymeleafEx02(Model model) {
		
	
		ItemDTO itemDTO = new ItemDTO();
		
		itemDTO.setItemNm("Test Product");
		itemDTO.setPrice(10000);
		itemDTO.setItemDetail("Test is hard");


		model.addAttribute("itemDTO",itemDTO);
		
		return "thymeleafEx/thymeleafEx02";
	}
	
	
	@GetMapping(value = "/ex03")
	public String thymeleafEx03(Model model) {
		
		List<ItemDTO> list = new ArrayList<ItemDTO>();
		for(int i=1; i<=10; i++) {
			
	
		ItemDTO itemDTO = new ItemDTO();
		
		itemDTO.setItemNm("Test Product" );
		itemDTO.setPrice(10000+i);
		itemDTO.setItemDetail("Test is hard"+i);
		list.add(itemDTO);
		}
		model.addAttribute("list",list);
		
		return "thymeleafEx/thymeleafEx03";
	}
	
	@GetMapping(value = "/ex04")
	public String thymeleafEx04(Model model) {
		
		List<ItemDTO> list = new ArrayList<ItemDTO>();
		for(int i=1; i<=10; i++) {
			
	
		ItemDTO itemDTO = new ItemDTO();
		
		itemDTO.setItemNm("Test Product" );
		itemDTO.setPrice(10000+i);
		itemDTO.setItemDetail("Test is hard"+i);

		list.add(itemDTO);
		}
		model.addAttribute("list",list);
		
		return "thymeleafEx/thymeleafEx04";
	}
	
	
	
	@GetMapping(value = "/ex05")
	public String thymeleafEx05(Model model) {
	
		
		return "thymeleafEx/thymeleafEx05";
	}
	
	
	@GetMapping(value = "/ex06")
	public String thymeleafEx06(String para1, String para2, Model model) {
		
		model.addAttribute("para1",para1);
		model.addAttribute("para2",para2);
		
		return "thymeleafEx/thymeleafEx06";
	}
	
	@GetMapping(value = "/ex07")
	public String thymeleafEx07(Model model) {
	
		
		
		return "thymeleafEx/thymeleafEx07";
	}
	

	
	
	

	
	
	
}
		

