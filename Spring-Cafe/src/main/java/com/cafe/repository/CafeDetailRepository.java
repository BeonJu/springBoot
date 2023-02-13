package com.cafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.entity.CafeDetail;

public interface CafeDetailRepository extends JpaRepository<CafeDetail, Long>{

}
