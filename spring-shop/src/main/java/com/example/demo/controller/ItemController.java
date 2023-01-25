package com.example.demo.controller;


import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import com.example.demo.dto.ItemFormDTO;
import com.example.demo.dto.ItemSearchDTO;
import com.example.demo.entity.Item;
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
	public String itemNew(@Valid ItemFormDTO itemFormDTO, BindingResult bindingResult,Model model, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList ) throws Exception {
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
	
	
	
	//상품 수정 페이지
	//@PathVariable("itemId")   /admin/item/{itemId}   = Long itemId 매칭
	@GetMapping(value = "/admin/item/{itemId}")
	public String itemDTL(@PathVariable("itemId") Long itemId, Model model) {
		try {	
			ItemFormDTO itemFormDTO = itemService.getItemDTL(itemId);
			model.addAttribute("ItemFormDTO",itemFormDTO);
		} catch (Exception e) {
			 model.addAttribute("errorMessage","존재하지 않는 상품입니다.");
			 model.addAttribute("ItemFormDTO",new ItemFormDTO());
			 return "item/itemForm";	
		}
		
		return "item/itemForm";
		
	}
	
	//상품 수정 하기
	@PostMapping(value = "/admin/item/{itemId}")
	public String itemUpdate( @Valid ItemFormDTO itemFormDTO, BindingResult bindingResult,Model model, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList ) throws Exception {
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
			itemService.updateItem(itemFormDTO, itemImgFileList);
			
		} catch (EntityNotFoundException e) {
			model.addAttribute("errorMessage","상품 수정 중 에러가 발생 했습니다.");
			
			return "item/itemForm";
		}
	
		return "redirect:/";

}
	@GetMapping(value = {"/admin/items", "/admin/items/{page}"})  //다중 맵핑, 페이지 번호가 없는 맵핑과 페이지 번호가 있는 맵핑
	public String itemManage(ItemSearchDTO itemSearchDTO, @PathVariable("page") Optional<Integer> page, Model model){
		//url경로에 페이지가 있으면 해당 페이지를 조회하도록 하고 페이지 번호가 없으면 0페이지를 조회하도록 한다
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0,3);   //페이지, 조회할  페이지의 번호, 한 페이지당 조회활 데이터의 갯수
		Page<Item> items = itemService.getAdminItemPage(itemSearchDTO, pageable);
		
		model.addAttribute("items",items);
		model.addAttribute("itemSearchDTO",itemSearchDTO);
		model.addAttribute("maxPage",5);
		
		return "item/itemMng";
		
		
		
	}
	
	//상품 상세 페이지
	@GetMapping(value = "/item/{itemId}")
	public String itemDTL(Model model, @PathVariable("itemId") Long itemId) {
		ItemFormDTO itemFormDTO =itemService.getItemDTL(itemId);
		model.addAttribute("item",itemFormDTO);
		
		return "item/itemDTL";
	}
	
	
	
	
	
}
