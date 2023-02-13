package com.cafe.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cafe.dto.CafeDetailDto;
import com.cafe.dto.CafeRegisterDto;
import com.cafe.dto.CafeSearchDto;
import com.cafe.dto.MainCafeDto;
import com.cafe.dto.CafeImgDto;
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
			cafeImg.setCafeId(cafeRegister);
			
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
	
	
	
	
	//카페 정보 가져오기(카페 상세 페이지 )
	@Transactional(readOnly = true) //트랜잭션 읽기 전용(변경감지 수행하지 않음) -> 성능향상
	public CafeRegisterDto getCafeDtl(Long cafeId) {
		//1. cafe_img테이블의 이미지를 id로 조회해서 가져온다.
		List<CafeImg> cafeImgList = cafeImgRepository.findByCafeIdOrderByIdAsc(cafeId);
		
		List<CafeImgDto> cafeImgDtoList = new ArrayList<>();
		
		//이미지 엔티티 객체 -> dto객체로 변환
		for(CafeImg cafeImg : cafeImgList) {
			CafeImgDto cafeImgDto = CafeImgDto.of(cafeImg);
			cafeImgDtoList.add(cafeImgDto);
		}
		
		//2. cafe테이블에 있는 데이터를 가져온다.
		CafeRegister cafeRegister = cafeRepository.findById(cafeId)
				.orElseThrow(EntityNotFoundException::new);
			

		//엔티티 객체 -> dto객체로 변환
		CafeRegisterDto cafeRegisterDto = CafeRegisterDto.of(cafeRegister);
		
		//상품의 이미지 정보를 넣어준다.
		cafeRegisterDto.setRegCafeImgDtoList(cafeImgDtoList);
		
		return cafeRegisterDto;
	}
	
	
	
	//메인 페이지 카페 리스트 불러오기
	@Transactional(readOnly = true)
	public Page<MainCafeDto> getMainCafeRegisterPage(CafeSearchDto cafeSearchDto, Pageable pageable){
		return cafeRepository.getMainCafeRegisterPage(cafeSearchDto, pageable);
	}
	
	
	
}
