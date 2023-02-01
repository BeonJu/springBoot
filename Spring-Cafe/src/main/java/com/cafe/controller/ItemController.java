package com.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class ItemController {
	
	@GetMapping(value = "/item/detail")
	public String itemDetail() {
		
		return "item/itemDetail";
	}
	
}