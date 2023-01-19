package com.example.demo.dto;


import org.modelmapper.ModelMapper;
import com.example.demo.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemImgDTD {

	private Long id;
	
	private String imgName;   //이미지 파일 명
	
	private String oriImgName;  //원본 파일 명
	
	private String imgUrl;  //이미지 조회 경로
	
	private String repimgYn;  //대표 이미지 여부
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static ItemImgDTD of(ItemImg itemImg) {
		return modelMapper.map(itemImg, ItemImgDTD.class);//맵핑 라우터 같은 기능
	} 
	
}
