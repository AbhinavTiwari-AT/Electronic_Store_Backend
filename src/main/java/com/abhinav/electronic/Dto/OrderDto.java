package com.abhinav.electronic.Dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderDto {
	

	private String orderId;
	
	private String orderStatu= "PENDING";
	
	private String paymentStatu = "NOTPAID";
	
	private int orderAmount;
	
	private String billingAddress;
	
	private String billingPhone;
	
	private Date orderedDate = new Date();
	
	private Date deliveredDate;
	
	//private UserDto user;
	
	private List<OrderItemDto> orderItems = new ArrayList<>();

}
