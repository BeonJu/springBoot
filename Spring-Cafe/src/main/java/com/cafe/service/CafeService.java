package com.cafe.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cafe.dto.CafeRegisterDto;
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
	private final CafeImgRepository cafeImgRepository;
	private final CafeImgService cafeImgService;
	
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
	
	
	
}
