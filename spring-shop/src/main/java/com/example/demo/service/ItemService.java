package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.ItemFormDTO;
import com.example.demo.dto.ItemImgDTD;
import com.example.demo.dto.ItemSearchDTO;
import com.example.demo.dto.MainItemDTO;
import com.example.demo.entity.Item;
import com.example.demo.entity.ItemImg;
import com.example.demo.repository.ItemImgRepository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.ItemRepositoryCustomImpl;

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
	
	
	
	//상품 가져오기
	@Transactional(readOnly = true)  //트랜젝션 읽기 전용, 가져오기만 하는 것이니 읽기 전용으로 선언하는 게 포퍼먼스가 좋다(변경 감지를 수행하지 않음)
	public ItemFormDTO getItemDTL(Long itemId){
		//item_img를 가져 오는 총 과정 start
		List<ItemImg> itemImgList  = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
		
		List<ItemImgDTD> itemImgDTDList = new ArrayList<>();
		
		//Entity 객체를 -> DTO 객체로 변환하는 과정
		for(ItemImg itemImg : itemImgList) {
			ItemImgDTD itemImgDTD = ItemImgDTD.of(itemImg);  //itemImgList를 itemImg 객체로 변환 후 ItemImgDTD객체 타입으로 itemImgDTD에 넣음
			itemImgDTDList.add(itemImgDTD);
		}
		//item_img를 가져 오는 총 과정 end
		
		
		
		//item table에 있는 테디이터를 가져 오는 과정 start
		Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
		//엔티티 -> DTO 변환
		ItemFormDTO itemFormDTO = ItemFormDTO.of(item);
		//상품의 이미지 정보를 넣어준다.
		itemFormDTO.setItemImgDTOList(itemImgDTDList);
				
		
		return itemFormDTO;
		
	}
	

	
	
	
	//상품 수정
		public Long updateItem(ItemFormDTO itemFormDTO, List<MultipartFile> itemImgFileList) throws Exception{
			Item item = itemRepository.findById(itemFormDTO.getId()).orElseThrow(EntityNotFoundException::new);
			
			item.updateItem(itemFormDTO);  //수정한 date를 updateitem 에 던짐
			
			List<Long> itemImgIds = itemFormDTO.getItemImgIds();  //상품 이미지 아이디 리스트를 조회
			
			for(int i=0; i<itemImgFileList.size(); i++) {
				itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
			}
			return item.getId();
		}
		
		@Transactional(readOnly = true)
		public Page<Item> getAdminItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable){
			
			return itemRepository.getAdminItemPage(itemSearchDTO, pageable);
		}
		
		
		@Transactional(readOnly = true)
		public Page<MainItemDTO> getMainItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable){
			
			return itemRepository.getMainItemPage(itemSearchDTO, pageable);
		}
		
}
