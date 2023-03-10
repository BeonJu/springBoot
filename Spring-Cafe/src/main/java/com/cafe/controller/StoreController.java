package com.cafe.controller;

import java.util.List;
import java.util.Optional;

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

import com.cafe.dto.CafeRegisterDto;
import com.cafe.dto.CafeSearchDto;
import com.cafe.dto.MainCafeDto;
import com.cafe.dto.StoreListDto;
import com.cafe.service.CafeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StoreController {
	
	private final CafeService cafeService;

	@GetMapping(value = "/admin/store/register")
	public String storeRegister(Model model) {
		model.addAttribute("cafeRegisterDto", new CafeRegisterDto());
		return "/store/storeRegister";
	}
	
	@PostMapping(value = "/admin/store/register")
	public String newStoreRegister(@Valid CafeRegisterDto cafeRegisterDto, BindingResult bindingResult, Model model, @RequestParam("cafeImgFile") List<MultipartFile> cafeImgFileList) throws Exception{
		
		//Valid 유효성 검증
		if(bindingResult.hasErrors()) {
			return "/store/storeRegister";
		}
		
		if(cafeImgFileList.get(0).isEmpty() && cafeRegisterDto.getId() == null) {
			//첫번쨰 값이 필수 값이기 때문에 첫번째 값만 검증함
			model.addAttribute("errorMessage","첫번째 가게 이미지는 필수 입력 값 입니다.");
			return "/store/storeRegister";
		}
		
		try {
			cafeService.regCafe(cafeRegisterDto, cafeImgFileList);
			
		} catch (Exception e) {
			model.addAttribute("errorMessage","가게 등록 중에 에러가 발생 했습니다.");
			return "/store/storeRegister";
		}
		
		return "redirect:/";
	}
	
	
	@GetMapping(value = "/admin/store/delete")
	public String storeDelete() {
		
		return "/store/storeDelete";
	}
	
	@GetMapping(value = "/admin/store/list")
	public String storeList(CafeSearchDto cafeSearchDto, Optional<Integer> page, Model model) {
		
		Pageable pageable = PageRequest.of(page.isPresent()? page.get() : 0, 6);
		Page<StoreListDto> list = cafeService.getStoreListPage(cafeSearchDto, pageable);
		
		model.addAttribute("list",list);
		model.addAttribute("cafeSearchDto",cafeSearchDto);
		model.addAttribute("maxPage",5);
		
		return "/store/storeList";
	}
	
	
	//가게 상세 페이지
	@GetMapping(value = "/admin/store/{listId}")
	public String itemDtl(Model model, @PathVariable("listId") Long cafeId) {
		CafeRegisterDto cafeRegisterDto = cafeService.getCafeDtl(cafeId);
				model.addAttribute("cafe", cafeRegisterDto);
		return "item/itemDetail";
	}
	

	
	
}
