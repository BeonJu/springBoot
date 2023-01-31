package com.example.demo.dto;

import javax.validation.constraints.*;


import lombok.*;

@Getter
@Setter
public class OrderDTO {
	
    @NotNull(message = "상품 아이디는 필수 입력 값입니다.")
    private Long itemId;

    @Min(value = 1, message = "최소 주문 수량은 1개 입니다.")
    @Max(value = 999, message = "최대 주문 수량은 999개 입니다.")
    
    private int count;
	
	
}
