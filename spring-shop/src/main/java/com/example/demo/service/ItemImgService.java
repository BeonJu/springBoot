package com.example.demo.service;


import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.example.demo.entity.ItemImg;
import com.example.demo.repository.ItemImgRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {

	@Value("${itemImgLocation}")
	private String itemImgLocation;    //C:shop/item
	
	private final ItemImgRepository itemImgRepository;
	
	private final FileService fileService;
	
	public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception{
		String oriImgName = itemImgFile.getOriginalFilename();
		String imgName="";
		String imgUrl = "";
		
		//파일 업로드
		if(!StringUtils.isEmpty(oriImgName)) {
			imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
			imgUrl = "/images/item/" + imgName;
		}
		
		//상품 이미지 정보 저장
		itemImg.updateItmeImg(oriImgName, imgName, imgUrl);
		itemImgRepository.save(itemImg);
		
	}
	
	
	//이미지 업데이트 메소드
	public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception{
		
		if(!itemImgFile.isEmpty()) { //파일이 있으면
			ItemImg savedItemImg = itemImgRepository.findById(itemImgId).orElseThrow(EntityNotFoundException::new);
			
			//기존 이미지 파일 삭제
			if(!StringUtils.isEmpty(savedItemImg.getImgName())) {
				fileService.deleteFile(itemImgLocation + "/" +savedItemImg.getImgName());
			}
			
			String oriImgName = itemImgFile.getOriginalFilename();
			String imgName= fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
			String imgUrl = "/images/item/" +imgName ;
			
			//파일 업로드
			if(!StringUtils.isEmpty(oriImgName)) {
				imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
				imgUrl = "/images/item/" + imgName;
			}
			
			savedItemImg.updateItmeImg(oriImgName, imgName, imgUrl);
			//savedItemImg 영속상태임으로 데이터 변경이 일어나서 변경 감지 기능이 실행되어 트렌젝션이 끝날 때 update 쿼리가 실행된다.
			
		}
	}
	
}
