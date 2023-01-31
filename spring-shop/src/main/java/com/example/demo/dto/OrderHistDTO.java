package com.example.demo.dto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.constance.OrderStatus;
import com.example.demo.entity.Order;

import lombok.*;


@Getter
@Setter
public class OrderHistDTO {
	//주문 정보 데이터를 담는 DTO
	  public OrderHistDTO(Order order){
	        this.orderId = order.getId();
	        this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	        this.orderStatus = order.getOrderStatus();
	    }

	    private Long orderId; //주문아이디
	    private String orderDate; //주문날짜
	    private OrderStatus orderStatus; //주문 상태

	    private List<OrderItemDTO> orderItemDtoList = new ArrayList<>();

	    //주문 상품리스트
	    public void addOrderItemDto(OrderItemDTO orderItemDto){
	        orderItemDtoList.add(orderItemDto);
	    }
}
