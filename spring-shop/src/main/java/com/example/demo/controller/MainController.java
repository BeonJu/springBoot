package com.example.demo.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ItemSearchDTO;
import com.example.demo.dto.MainItemDTO;
import com.example.demo.service.ItemService;
import com.example.demo.service.MemberService;



import lombok.RequiredArgsConstructor;

@Controller //컨트롤러의 역할을 하는 클래스를 정의
@RequiredArgsConstructor
@RequestMapping(value = "/") //request url 경로지정
public class MainController {
	private final ItemService itemService;
	
	@GetMapping(value = "/")
	public String main(ItemSearchDTO itemSearchDTO, Optional<Integer> page, Model model ) {
		Pageable pageable = PageRequest.of(page.isPresent()? page.get() : 0, 6);
		Page<MainItemDTO> items = itemService.getMainItemPage(itemSearchDTO, pageable);
		model.addAttribute("items",items);
		model.addAttribute("itemSearchDTO", itemSearchDTO);
		model.addAttribute("maxPage",5);
	
		return "Main";

	}
	

}
