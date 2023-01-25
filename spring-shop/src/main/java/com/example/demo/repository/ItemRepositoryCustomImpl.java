package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.example.demo.constance.ItemSellStatus;
import com.example.demo.dto.ItemSearchDTO;
import com.example.demo.dto.MainItemDTO;
import com.example.demo.dto.QMainItemDTO;
import com.example.demo.entity.Item;
import com.example.demo.entity.QItem;
import com.example.demo.entity.QItemImg;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom{

	private JPAQueryFactory queryFactory;
	
	public ItemRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	
	//조회 시간
	private BooleanExpression regDtsAfter(String searchDateType) {
		LocalDateTime dateTime = LocalDateTime.now();
		if(StringUtils.equals("all", searchDateType) || searchDateType == null ) {
			return null;
		}
		else if(StringUtils.equals("1d", searchDateType) || searchDateType == null) {
			dateTime = dateTime.minusDays(1); }
		else if(StringUtils.equals("1w", searchDateType)) {
			dateTime = dateTime.minusWeeks(1); }
		else if(StringUtils.equals("1m", searchDateType)) {
			dateTime = dateTime.minusMonths(1); }
		else if(StringUtils.equals("6m", searchDateType)) {
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
				.where(regDtsAfter(itemSearchDTO.getSearchDateType()),
						searchSellStatusEq(itemSearchDTO.getSearchSellStatus()),
						searchByLike(itemSearchDTO.getSearchBy(), itemSearchDTO.getSearchQuery()))  //itemNm or createBy , like ('%searchQuery%')
				.orderBy(QItem.item.id.asc())
				.offset(pageable.getOffset())  //데이터를 가져올 시작 index
				.limit(pageable.getPageSize())  // 한번에 보여줄 page 최대 가짓수
				.fetch();
		
		//https://querydsl.com/static/querydsl/4.1.0/apidocs/com/querydsl/core/types/dsl/Wildcard.html
				// Wildcard.count = count(*)
				long total = queryFactory.select(Wildcard.count).from(QItem.item)  //전체 레코드(튜플) 카운팅 갯수 
		                .where(regDtsAfter(itemSearchDTO.getSearchDateType()),
		                        searchSellStatusEq(itemSearchDTO.getSearchSellStatus()),
		                        searchByLike(itemSearchDTO.getSearchBy(), itemSearchDTO.getSearchQuery()))
		                .fetchOne();   // fetch는 레코드 내용을 반환, fetchOne은 한건 이상일 경우 자료형을 반환
		
		return new PageImpl<>(content, pageable, total);
		
	}


	private BooleanExpression itemNmLike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery) ? null: QItem.item.itemNm.like("%" + searchQuery + "%");
	}
	
	
	
	
	
	
	
	
	
	@Override
	public Page<MainItemDTO> getMainItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable) {
		
		QItem item = QItem.item;
		QItemImg itemImg = QItemImg.itemImg;
		
		List<MainItemDTO> content = queryFactory.select(new QMainItemDTO(item.id, item.itemNm, item.itemDetail, itemImg.imgUrl, item.price))
				.from(itemImg)
				.join(itemImg.item, item)
				.where(itemImg.repimgYn.eq("Y"))
				.where(itemNmLike(itemSearchDTO.getSearchQuery()))
				.orderBy(item.id.desc())
				.offset(pageable.getOffset())  //데이터를 가져올 시작 index
				.limit(pageable.getPageSize())  // 한번에 보여줄 page 최대 가짓수
				.fetch();
		
		long total = queryFactory.select(Wildcard.count).from(itemImg)
				.join(itemImg.item, item)
				.where(itemImg.repimgYn.eq("Y"))
				.where(itemNmLike(itemSearchDTO.getSearchQuery()))
				.fetchOne();

		return new PageImpl<>(content, pageable, total);
	}

}
