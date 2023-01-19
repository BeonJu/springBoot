package com.example.demo.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.*;


@EntityListeners(value = {AuditingEntityListener.class}) //Auding 적용 하기 위한 어노테이션
@MappedSuperclass   //부모 클래스를 상속받는 자식 클래스한데 매핑정도만 제공
@Getter
@Setter
public class BaseTimeEntity {
	
	@CreatedDate   //엔티티가 생성되서 저장 될때 시간을 자동으로 저장
	@Column(updatable = false)  //수정 불가 설정, 등록만 됨
	private LocalDateTime regTime;   
	
	@LastModifiedDate
	@Column
	private LocalDateTime upDateTime;
	
	

}
