package com.cafe.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cafe.dto.CafeSearchDto;
import com.cafe.dto.MainCafeDto;
import com.cafe.service.CafeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/")   //Request URL root(/)지정
public class MainController {
	private final CafeService cafeService;
	
	@GetMapping(value = "/")
	public String main(CafeSearchDto cafeSearchDto, Optional<Integer> page, Model model) {
		Pageable pageable = PageRequest.of(page.isPresent()? page.get() : 0, 6);
		Page<MainCafeDto> cafes = cafeService.getMainCafeRegisterPage(cafeSearchDto, pageable);
		
		model.addAttribute("cafes",cafes);
		model.addAttribute("cafeSearchDto",cafeSearchDto);
		model.addAttribute("maxPage",5);
		
		return "/mainList/main";
	}
}
