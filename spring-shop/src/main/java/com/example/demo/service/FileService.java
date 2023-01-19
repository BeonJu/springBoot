package com.example.demo.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.extern.java.Log;

@Service
@Log
public class FileService {
//파일 업로드
	public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception{
		UUID uuid = UUID.randomUUID(); //중복 되지 않는 랜덤한 파일 이름 생성
		
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));   //확장자명 제거
		String saveFileName = uuid.toString() + extension; // 파일 이름 생성
		String fileUploadFullUrl = uploadPath + "/" + saveFileName;
		
		//생성자에 파일이 저장 될 위치와 파일 이름을 같이 넘겨 출력스트림을 만든다.
		FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
		fos.write(fileData);
		fos.close();
		
		return saveFileName;
		
	}
	
	
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
