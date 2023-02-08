package com.cafe.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.extern.java.Log;

@Service
@Log
public class FileService {

	//파일 업로드
		public String uploadFile(String uploadPath, String originalFileName, byte[] fileDate) throws Exception{
			UUID uuid  = UUID.randomUUID();  //중복 되지 않는 랜덤한 복호화 파일 이름 생성
			
			String extension = originalFileName.substring(originalFileName.lastIndexOf("."));  // .뒤에 확장자 제거
			String saveFileName = uuid.toString() + extension; //uuid와 확장자명을 제거한 extension 을 합쳐서 파일명 지정
			String fileUploadFullUrl = uploadPath + "/" + saveFileName;      // uploadPath=file:///D:/cafe/
			
			FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);  //파일 output 스트림 객체를 만들고 full url을 넣음
			fos.write(fileDate);   //full url 경로에 바이트 단위로 이미지 데이터를 씀
			fos.close();  //output Stream 닫음
		
			return saveFileName;
		}
		
		//파일 삭제
		public void deleteFile(String filePath) throws Exception{
			File deleteFile = new File(filePath);
			
			if(deleteFile.exists()) { //해당 경로에 파일이 있으면
				deleteFile.delete();  //삭제
				log.info(deleteFile.toString() + "파일을 삭제하였습니다.");
			} else {
				log.info("파일이 존재 하지 않습니다.");
			}
		}
	
}
