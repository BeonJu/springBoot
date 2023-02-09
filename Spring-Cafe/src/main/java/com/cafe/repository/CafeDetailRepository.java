package com.cafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.dto.CafeDetailDto;

public interface CafeDetailRepository extends JpaRepository<CafeDetailDto, Long>{

}
