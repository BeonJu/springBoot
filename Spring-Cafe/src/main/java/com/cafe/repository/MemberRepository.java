package com.cafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.entity.Member;

public interface MemberRepository  extends JpaRepository<Member, Long>{
	//회원가입 시 이메일 파라메터로 중복 이메일 값이 있는지 검사한다, ex)select * from member where member.email = email;
	Member findByEmail(String email); 
	
}
