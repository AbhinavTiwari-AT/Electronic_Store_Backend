package com.abhinav.electronic.Service;

import java.util.List;

import com.abhinav.electronic.Dto.CreateOrderRequest;
import com.abhinav.electronic.Dto.OrderDto;
import com.abhinav.electronic.Dto.PagebleResponse;
import com.abhinav.electronic.Dto.UpdateOrderRequestDto;

public interface OrderService 
{
	//create order
	OrderDto createOrder(CreateOrderRequest orderDto);
	
	//order remove
	void removOrder(String orderId);
	 
	//get order of user
	List<OrderDto> getOrdersOfUser(String userId);
	 
	//get orders
	PagebleResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir );
	
	//other methods(Logic) related to Order
	
	OrderDto updateOrder(String orderId, UpdateOrderRequestDto updateRequest);
	
}
