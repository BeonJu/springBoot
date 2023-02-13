package com.cafe.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.cafe.dto.CafeSearchDto;
import com.cafe.dto.MainCafeDto;
import com.cafe.dto.QMainCafeDto;
import com.cafe.entity.CafeImg;
import com.cafe.entity.QCafeImg;
import com.cafe.entity.QCafeRegister;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

//페이징, 검색
public class CafeRepositoryCustomImpl implements CafeRepositoryCustom {

	private JPAQueryFactory queryFactory;
	
	
	public CafeRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

//	private BooleanExpression businessNameLike(String searchQuery) {
//		return StringUtils.isEmpty(searchQuery) ? null : QCafeRegister.cafeRegister.businessName.like("%" + searchQuery + "%");
//	}

	@Override
	public Page<MainCafeDto> getMainCafeRegisterPage(CafeSearchDto cafeSearchDto, Pageable pageable) {
	
		QCafeRegister cafeRegister = QCafeRegister.cafeRegister;
		QCafeImg cafeImg = QCafeImg.cafeImg;
		
		
		//전체 카페 상호명, 주소 카페 대표 이미지 불러 오기
		List<MainCafeDto> content = queryFactory.select(new QMainCafeDto(cafeRegister.id, cafeRegister.businessName, cafeRegister.address, cafeImg.imgUrl ))
				.from(cafeImg)
				.join(cafeImg.cafeId, cafeRegister)
				.where(cafeImg.repimgYn.eq("Y"))
				//.where(businessNameLike(cafeSearchDto.getSearchQuery()))
				.orderBy(cafeRegister.id.desc())
				.offset(pageable.getOffset()) //데이터를 가져올 시작 index
				.limit(pageable.getPageSize()) // 한번에 보여줄 page 최대 가짓수
				.fetch();
		
		
		//컨텐츠 수랑 페이지 맥시멈 비교
		long total = queryFactory.select(Wildcard.count).from(cafeImg)
				.join(cafeImg.cafeId, cafeRegister)
				.where(cafeImg.repimgYn.eq("Y"))
				//.where(businessNameLike(cafeSearchDto.getSearchQuery()))
				.fetchOne();  // fetch는 레코드 내용을 반환, fetchOne은 한건 이상일 경우 자료형을 반환
		
		return new PageImpl<>(content, pageable, total);
	}


	
	
	
	
}
