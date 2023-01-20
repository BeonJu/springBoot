package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.example.constance.ItemSellStatus;
import com.example.demo.dto.ItemSearchDTO;
import com.example.demo.entity.Item;
import com.example.demo.entity.QItem;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom{

	private JPAQueryFactory queryFactory;
	
	public ItemRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	
	//조회 시간
	private BooleanExpression regDtsAfter(String searchDataType) {
		LocalDateTime dateTime = LocalDateTime.now();
		if(StringUtils.equals("all", searchDataType) || searchDataType == null ) {
			return null;
		}
		else if(StringUtils.equals("1d", searchDataType) || searchDataType == null) {
			dateTime = dateTime.minusDays(1); }
		else if(StringUtils.equals("1w", searchDataType)) {
			dateTime = dateTime.minusWeeks(1); }
		else if(StringUtils.equals("1m", searchDataType)) {
			dateTime = dateTime.minusMonths(1); }
		else if(StringUtils.equals("6m", searchDataType)) {
			dateTime = dateTime.minusMonths(6); }
		
		
		return QItem.item.regTime.after(dateTime); //이후의 시간
		
	}
	
	// 판매 품절
	private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus) {
		return   searchSellStatus == null ? null:QItem.item.itemSellStatus.eq(searchSellStatus);
	}
	
	private BooleanExpression searchByLike(String searchBy, String searchQuery) {
		 if(StringUtils.equals("itemNm", searchBy)) {
			 return QItem.item.itemNm.like("%" + searchQuery + "%");
		 }else if(StringUtils.equals("createBy", searchBy)) {
			 return QItem.item.createBy.like("%" + searchQuery+ "%");
		 }
		
		 return null;
	}
	
	
	@Override
	public Page<Item> getAdminItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable) {
		List<Item> content = queryFactory
				.selectFrom(QItem.item) //select * from item
				.where(regDtsAfter(itemSearchDTO.getSearchDataType()),
						searchSellStatusEq(itemSearchDTO.getSearchSellStatus()),
						searchByLike(itemSearchDTO.getSearchBy(), itemSearchDTO.getSearchQuery()))  //itemNm or createBy , like ('%searchQuery%')
				.orderBy(QItem.item.id.asc())
				.offset(pageable.getOffset())  //데이터를 가져올 시작 index
				.limit(pageable.getPageSize())  // 한번에 보여줄 page 최대 가짓수
				.fetch();
		
		long total =  content.size();
		
		return new PageImpl<>(content, pageable, total);
		
	}

}
