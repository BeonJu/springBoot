package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "item_img")
@Getter
@Setter
public class ItemImg extends BaseEntity{

	@Id
	@Column(name="member_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	private String imgName;   //이미지 파일 명
	
	private String oriImgName;  //원본 파일 명
	
	private String imgUrl;  //이미지 조회 경로
	
	private String repimgYn;  //대표 이미지 여부
	
	@ManyToOne(fetch = FetchType.LAZY)  //지연로딩  애만 가져 올거임
	@JoinColumn(name = "item_id")
	private Item item;
	
	// 원본 이미지 파일명, 업데이트 할 이미지 파일명
	//이미지 경로를 파라메터로 받아서 이미지 정보를 업데이트 하는 메소스 
	public void updateItmeImg(String oriImgName, String imgName, String imgUrl) {
		this.imgName  = imgName;
		this.oriImgName = oriImgName;
		this.imgUrl = imgUrl;
		
	}
	
}
