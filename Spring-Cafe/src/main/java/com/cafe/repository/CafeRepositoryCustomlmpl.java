package com.cafe.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.swing.text.TabableView;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.cafe.dto.CafeSearchDto;
import com.cafe.dto.MainCafeDto;
import com.cafe.dto.QMainCafeDto;
import com.cafe.entity.CafeRegister;
import com.cafe.entity.QCafeImg;
import com.cafe.entity.QCafeRegister;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

//페이징, 검색
public class CafeRepositoryCustomlmpl implements CafeRepositoryCustom {

	private JPAQueryFactory queryFactory;
	
	public CafeRepositoryCustomlmpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Page<CafeRegister> getAdminItemPage(CafeSearchDto cafeSearchDto, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private BooleanExpression businessNameLike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery) ? null : QCafeRegister.cafeRegister.businessName.like("%" + searchQuery + "%");
	}

	@Override
	public Page<MainCafeDto> getMainItemPage(CafeSearchDto cafeSearchDto, Pageable pageable) {
		
		QCafeRegister cafeRegister = QCafeRegister.cafeRegister;
		QCafeImg cafeImg = QCafeImg.cafeImg;
		
		//카페 상호명, 주소 카페 대표 이미지 불러 오기
		List<MainCafeDto> content = queryFactory.select(new QMainCafeDto(cafeRegister.id, cafeRegister.businessName, cafeRegister.address, cafeImg.imgUrl ))
				.from(cafeImg)
				.join(cafeImg.cafeRegister, cafeRegister)
				.where(cafeImg.repimgYn.eq("Y"))
				.where(businessNameLike(cafeSearchDto.getSearchQuery()))
				.orderBy(cafeRegister.id.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		
		//컨텐츠 수랑 페이지 맥시멈 비교
		long total = queryFactory.select(Wildcard.count).from(cafeImg)
				.join(cafeImg.cafeRegister, cafeRegister)
				.where(cafeImg.repimgYn.eq("Y"))
				.where(businessNameLike(cafeSearchDto.getSearchQuery()))
				.fetchOne();
		
		return new PageImpl<>(content, pageable, total);
	}
	
	
}
