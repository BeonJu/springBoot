package com.cafe.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.cafe.entity.CafeImg;
import com.cafe.repository.CafeImgRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CafeImgService {

	@Value("${cafeImgLocation}")
	private String cafeImgLocation;   //D:cafe/img
	
	private final CafeImgRepository cafeImgRepository;
	
	private final FileService fileService;
	
	//이미지 저장 메소드
	public void saveCafeImg(CafeImg cafeImg, MultipartFile cafeImgFIle) throws Exception{
		String oriImgName = cafeImgFIle.getOriginalFilename();
		String imgName="";
		String imgUrl = "";
		
		//파일 업로드
		if(!StringUtils.isEmpty(oriImgName)) {
			imgName = fileService.uploadFile(cafeImgLocation, oriImgName, cafeImgFIle.getBytes());
			imgUrl = "/images/" + imgName;
		}
		
		//상품 이미지 정보 저장
		cafeImg.updateCafeImg(oriImgName, imgName, imgUrl);
		cafeImgRepository.save(cafeImg);
	}
	
	
//	//이미지 변경 메소드
//	public void updateCafeImg(Long cafeImgid, MultipartFile cafeImgFile) throws Exception{
//		CafeImg saveCafeImg = cafeImgRepository.findById(cafeImgid).orElseThrow(EntityNotFoundException::new);   //EntityNotFoundException 에러를 처리하고 만약 에러가 발생하면 model에 담아서 반환 
//	
//		//기존 이미지 파일 삭제
//	if(!StringUtils.isEmpty(saveCafeImg.getImgName())) {
//		fileService.deleteFile(cafeImgLocation + "/" + saveCafeImg.getImgName());
//	}
//	
//	//수정된 이미지 파일 업로드
//	String oriImgName = cafeImgFile.getOriginalFilename();
//	String imgName  = fileService.uploadFile(cafeImgLocation, oriImgName, cafeImgFile.getBytes());
//	String imgUrl = "/img/gallery/" + imgName;
//	
//	//savedItemImg는 현재 영속상태이므로 데이터를 변경하는 것만으로 변경감지 기능이 동작하여 트랜잭션이 끝날때 update쿼리가 실행된다.
//	//-> 엔티티가 반드시 영속상태여야 한다.
//	saveCafeImg.updateCafeImg(oriImgName, imgName, imgUrl);
	
//}
}
