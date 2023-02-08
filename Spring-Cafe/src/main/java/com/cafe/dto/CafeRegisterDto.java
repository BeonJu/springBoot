package com.cafe.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;

import com.cafe.entity.CafeRegister;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CafeRegisterDto {
	
	
	private Long id;

	@NotBlank(message = "상호명은 필수 입력 값 입니다.")
	private String businessName;
	
	@NotBlank(message = "점주 성명은 필수 입력 값 입니다.")
	private String ownerName;

	@NotBlank(message = "연락처는 필수 입력 값 입니다.")
	private String phone;

	@NotBlank(message = "주소는 필수 입력 값 입니다.")
	private String address;

	private String info;
	
	private List<regCafeImgDto> regCafeImgDtoList = new ArrayList<>();  //카페의 이미지 정보를 저장하는 리스트, 하나의 카페에 이미지는 다수 
	
	private List<Long> refCafeImgId = new ArrayList<>();   //카페 이미지 id를 저장, 수정 시에 이미지 id를 담아주는 용도
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public CafeRegister createCafe() {
		return modelMapper.map(this,CafeRegister.class);
	}
	
	public static CafeRegisterDto of(CafeRegister cafeRegister) {
		return modelMapper.map(cafeRegister, CafeRegisterDto.class);
	}
	
	
	
}
