package com.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StoreController {

	@GetMapping(value = "/admin/store/register")
	public String storeRegister() {
		
		return "/store/storeRegister";
	}
	
	@GetMapping(value = "/admin/store/delete")
	public String storeDelete() {
		
		return "/store/storeDelete";
	}
	
	
}
