package com.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")   //Request URL root(/)지정
public class MainController {
	
	@GetMapping(value = "/")
	public String mainCall() {
		return "/mainList/main";
	}
}
