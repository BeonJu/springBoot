package com.cafe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.entity.StoreMenu;

public interface StoreMenuRepository extends JpaRepository<StoreMenu, Long>{

	List<StoreMenu> findByCafeId(Long cafeId);
}
