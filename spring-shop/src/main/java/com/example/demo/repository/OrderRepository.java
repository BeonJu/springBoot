package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	
	//추상 메소드, 현재 로그인한 사용자의 데이터를 페이징 조건에 맞춰서 주문 목록을 가져오는 메소드
	@Query("select o from Order o where o.member.email = :email order by o.orderDate desc")   //
	List<Order> findOrders(@Param("email") String email, Pageable pageable);
	
	
	//현재 로그인한 사용자의 주문 개수가 몇개인지 조회
	@Query("select count(o) from Order o where o.member.email = :email") 
	Long countOrder(String email);
	
}
