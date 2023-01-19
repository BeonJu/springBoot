package com.example.demo.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.ItemFormDTO;
import com.example.demo.service.ItemService;

import lombok.RequiredArgsConstructor;



@Controller //컨트롤러의 역할을 하는 클래스를 정의	
@RequiredArgsConstructor
public class ItemController {
	private final ItemService itemService;
	
	
	
	//상품등록 page show
	@GetMapping(value = "/admin/item/new")
	public String itemForm(Model model) {
		model.addAttribute("ItemFormDTO",new ItemFormDTO());   // 텅빈 DTO 객체 itemForm으로 를 보냄
		return "item/itemForm";
	}
	
	
	
	//상품등록
	@PostMapping(value = "/admin/item/new")
	public String itemNew(@Valid ItemFormDTO itemFormDTO, BindingResult bindingResult,Model model, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList ) {
		//Valid 유효성 검증
		if(bindingResult.hasErrors()) {
			return "item/itemForm";
		}
		
		if(itemImgFileList.get(0).isEmpty() && itemFormDTO.getId() == null) {
			//List 의 첫번째 값이 필수 값이기 때문에 첫번쨰 값을 검증 함
			model.addAttribute("errorMessage","첫번째 상품 이미지는 필수 입력 값 입니다.");
			return "item/itemForm";
		}
		
		try {
			itemService.saveItem(itemFormDTO, itemImgFileList);
			
			
		} catch (Exception e) {
			model.addAttribute("errorMessage","상품 등록 중에 에러가 발생 했습니다.");
			return "item/itemForm";
		}
		
		
		return "redirect:/";
		
		
	}
	
	

}
