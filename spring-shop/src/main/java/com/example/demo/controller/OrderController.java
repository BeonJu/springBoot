package com.example.demo.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.OrderHistDTO;
import com.example.demo.service.OrderService;


import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrderController {
	
	private final OrderService orderService;
	
    @PostMapping(value = "/order")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid OrderDTO orderDto
            , BindingResult bindingResult, Principal principal){

        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }

            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = principal.getName();
        Long orderId;

        try {
            orderId = orderService.order(orderDto, email);
        } catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }
    
    
    @GetMapping(value = {"/orders", "/orders/{page}"})
    public String orderHist(@PathVariable("page") Optional<Integer> page, Principal principal, Model model) {
    	Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0,3);   //페이지, 조회할  페이지의 번호, 한 페이지당 조회활 데이터의 갯수
   
    	Page<OrderHistDTO> orderHistList = orderService.getOrderList(principal.getName(), pageable);
    	
    	model.addAttribute("orders",orderHistList);
    	model.addAttribute("page",pageable.getPageNumber());
    	model.addAttribute("maxPage",5);
    	
    	return "order/orderHist";
    
    
    }
    
    @PostMapping("/order/{orderId}/cancel")
    public @ResponseBody ResponseEntity cancelOrder(@PathVariable("orderId") Long orderId, Principal principal) {
    	if(!orderService.validateOrder(orderId, principal.getName())) {
    		return new ResponseEntity<String>("주문 취소 권한이 없습니다.", HttpStatus.FORBIDDEN);
    	}
    	orderService.cancelOrder(orderId);  //주문 취소 메소드
    	
		return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    	
    }
    
    @DeleteMapping("/order/{orderId}/delete")
    public @ResponseBody ResponseEntity deleteOrder(@PathVariable("orderId") Long orderId, Principal principal) {
    	if(!orderService.validateOrder(orderId, principal.getName())) {
    		return new ResponseEntity<String>("주문 삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);
    	}
    	orderService.deleteOrder(orderId);
    	
    	return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

}