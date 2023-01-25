package com.example.demo.dto;


import com.querydsl.core.annotations.QueryProjection;

import lombok.*;


@Getter
@Setter
public class MainItemDTO {

    private Long id;

    private String itemNm;

    private String itemDetail;

    private String imgUrl;

    private Integer price;

    
    @QueryProjection  //쿼리 dsl로 조회를 할 때 DTO를 바로 받아 올수 있다
    public MainItemDTO(Long id, String itemNm, String itemDetail, String imgUrl,Integer price){
        this.id = id;
        this.itemNm = itemNm;
        this.itemDetail = itemDetail;
        this.imgUrl = imgUrl;
        this.price = price;
    }

}