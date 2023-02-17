package com.share.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")   //Request URL root(/)지정
public class TestController {
	
//	@GetMapping(value = "/")
//	public String main() {
//		
//		return "TestModal";
//		
//	}
//	
	@GetMapping(value = "/")
	public String list() {
		
		return "giveProductList";
		
	}
	

}
