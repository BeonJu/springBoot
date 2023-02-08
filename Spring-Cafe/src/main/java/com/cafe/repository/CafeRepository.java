package com.cafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.cafe.entity.CafeRegister;



public interface CafeRepository extends JpaRepository<CafeRegister, Long>, QuerydslPredicateExecutor<CafeRegister>, CafeRepositoryCustom{

}
