package com.example.demo.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@EntityListeners(value = {AuditingEntityListener.class}) //Auding 적용 하기 위한 어노테이션
@MappedSuperclass   //부모 클래스를 상속받는 자식 클래스한데 매핑정도만 제공
@Getter
@Setter
public class BaseEntity extends BaseTimeEntity {
	
	
	@CreatedBy
	@Column(updatable = false)
	private String createBy; //수정자
	
	@LastModifiedBy
	private String modifiedBy; //수정자

}
