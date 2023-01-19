package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.ItemFormDTO;
import com.example.demo.entity.Item;
import com.example.demo.entity.ItemImg;
import com.example.demo.repository.ItemImgRepository;
import com.example.demo.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

	private final ItemRepository itemRepository;
	private final ItemImgService itemImgService;
	private final ItemImgRepository itemImgRepository;
	
	//상품 등록
	public Long saveItem(ItemFormDTO itemFormDTO, List<MultipartFile> itemImgFileList) throws Exception{
		// 상품 등록
		Item item = itemFormDTO.createItem();
		itemRepository.save(item);
		
		// 이미지 등록
		for(int i=0; i<itemImgFileList.size(); i++) {
			ItemImg itemImg = new ItemImg();
			itemImg.setItem(item);
			
			if(i == 0) {   //첫 번째 이미지 일때 대표 상품 이미지 여부 지정
				itemImg.setRepimgYn("Y");
			}else {
				itemImg.setRepimgYn("N");
			}
			
			
			itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
			
		}
		
		return item.getId();
		

	}
	
}
