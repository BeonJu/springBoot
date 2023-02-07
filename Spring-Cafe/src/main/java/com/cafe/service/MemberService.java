package com.cafe.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafe.entity.Member;
import com.cafe.repository.MemberRepository;


import lombok.RequiredArgsConstructor;

@Service
@Transactional // service class logic 작업 단위로 rollback or commit을 하기 위하여 처리 단위 지정
@RequiredArgsConstructor //의존성 주입 시 final을 붙일 시 사용, 아니면 autouwire 사용 
public class MemberService  implements UserDetailsService{
	
	private final MemberRepository memberRepository; //싱글톤 의존성 주입

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		 //로그인시(request) 넘어온 사용자 정보를 받음
		Member member = memberRepository.findByEmail(email);
		
		if(member == null) {
			throw new UsernameNotFoundException(email);
		}
		
		//UserDetail를 상속받은 User 객체에 DB에서 받은 사용자 정보를 넣는다 = UserDetail 객체에 반환
		return User.builder()
				.username(member.getEmail())
				.password(member.getPassword())
				.roles(member.getRole().toString())
				.build();
		
	}
	
	public Member saveMember(Member member) {
		
		validateDuplicateMember(member);
		return memberRepository.save(member);   //dbms in member table insert 
	}
	
	//이메일 중복체크 메소드
	private void validateDuplicateMember(Member member){
		Member findMember = memberRepository.findByEmail(member.getEmail()); //회원가입 시 이메일 중복값 검증
		if(findMember != null) {
			throw new IllegalStateException("이미 가입된 회원입니다.");
		}
		
	}
	
}
