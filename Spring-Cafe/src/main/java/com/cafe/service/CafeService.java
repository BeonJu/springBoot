package com.cafe.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cafe.dto.CafeDetailDto;
import com.cafe.dto.CafeRegisterDto;
import com.cafe.dto.CafeSearchDto;
import com.cafe.dto.MainCafeDto;
import com.cafe.entity.CafeImg;
import com.cafe.entity.CafeRegister;
import com.cafe.repository.CafeImgRepository;
import com.cafe.repository.CafeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CafeService {
	
	private final CafeRepository cafeRepository;
	private final CafeImgService cafeImgService;
	private final CafeImgRepository cafeImgRepository;
	
	//가게 등록
	public Long regCafe(CafeRegisterDto cafeRegisterDto, List<MultipartFile> cafeImgFileList) throws Exception{
		
		//카페 생성 및 등록
		CafeRegister cafeRegister  = cafeRegisterDto.createCafe();
		cafeRepository.save(cafeRegister);
		
		//이미지 등록
		for(int i=0; i< cafeImgFileList.size(); i++) {
			CafeImg cafeImg = new CafeImg();
			cafeImg.setCafeRegister(cafeRegister);
			
			//첫번째 이미지 일때 애표 상품 이미지 여부 지정
			if( i==0) {
				cafeImg.setRepimgYn("Y");
			} else {
				cafeImg.setRepimgYn("N");
			}
			
			cafeImgService.saveCafeImg(cafeImg, cafeImgFileList.get(i));
		}
		return cafeRegister.getId();
	}
	
	
	
	
	//상품 가져오기(카페 상세 페이지 )
	@Transactional(readOnly = true) //트랜잭션 읽기 전용(변경감지 수행하지 않음) -> 성능향상
	public CafeDetailDto getItemDtl(Long itemId) {
		//1. item_img테이블의 이미지를 가져온다.
		List<CafeImg> cafeImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
		List<CafeDetailDto> cafeImg	DtoList = new ArrayList<>();
		
		//엔티티 객체 -> dto객체로 변환
		for(ItemImg itemImg : itemImgList) {
			ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
			itemImgDtoList.add(itemImgDto);
		}
		
		//2. item테이블에 있는 데이터를 가져온다.
		Item item = itemRepository.findById(itemId)
				                  .orElseThrow(EntityNotFoundException::new);
		
		//엔티티 객체 -> dto객체로 변환
		ItemFormDto itemFormDto = ItemFormDto.of(item);
		
		//상품의 이미지 정보를 넣어준다.
		itemFormDto.setItemImgDtoList(itemImgDtoList);
		
		return itemFormDto;
	}
	
	
	
	
	@Transactional(readOnly = true)
	public Page<MainCafeDto> getMainCafeRegisterPage(CafeSearchDto cafeSearchDto, Pageable pageable){
		return cafeRepository.getMainCafeRegisterPage(cafeSearchDto, pageable);
	}
	
	
	
}
