package com.example.demo.entity;



import javax.persistence.*;



import lombok.*;


@Table(name = "Cart")  //테이블 명 지정
@Entity
@Getter
@Setter
@ToString
public class Cart {

	
	@Id
	@Column(name = "cart_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//member.java에 member_id column과 참조 관계성을 선언 한다. foreign key
	@JoinColumn(name = "member_id")
	@OneToOne(fetch = FetchType.LAZY)   // 일 대 일 관계 선언
	private Member member;
	
	
	
	
}
