package com.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cafe.dto.CafeRegisterDto;
import com.cafe.service.CafeService;

@Controller
public class ItemController {
	
	@GetMapping(value = "/item/detail")
	public String itemDetail() {
		
		return "item/itemDetail";
	}
	
	@GetMapping(value = "/item/reservation")
	public String itemReservation() {
		
		return "item/itemReservation";
	}
	
	@GetMapping(value = "/item/check")
	public String itemCheck() {
		
		return "item/itemReservationCheck";
	}
	
	//상품 상세 페이지
		@GetMapping(value = "/item/{cafeId}")
		public String itemDtl(Model model, @PathVariable("cafeId") Long itemId) {
			CafeRegisterDto cafeRegisterDto = CafeService.getItemDtl(itemId);
			model.addAttribute("item", itemFormDto);
			return "item/itemDtl";
		}
}
