package com.cafe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.cafe.dto.MainCafeDto;
import com.cafe.entity.CafeRegister;


public interface CafeRepository extends JpaRepository<CafeRegister, Long>, QuerydslPredicateExecutor<CafeRegister>, CafeRepositoryCustom{

	
	List<CafeRegister> findByBusinessNameLike(String businessName);
	
	
}
