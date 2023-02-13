package com.cafe.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CafeDetail {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private Long id;
	
	private String businessName;
	
	private String address;
	
	private String phone;
	
	private String info;
	
	//대표 이미지 imgUrl
	private String imgUrlY;
	
	//대표가 아닌 이미지로 슬라이드에 사용 될 것 imgUrl
	@OneToMany(targetEntity = CafeImg.class)
	private List<String> imgUrlN = new ArrayList<>();
	
	
}
