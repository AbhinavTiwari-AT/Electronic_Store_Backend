package com.abhinav.electronic.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abhinav.electronic.Config.AppConstants;
import com.abhinav.electronic.Dto.ApiResponseMessage;
import com.abhinav.electronic.Dto.CreateOrderRequest;
import com.abhinav.electronic.Dto.OrderDto;
import com.abhinav.electronic.Dto.PagebleResponse;
import com.abhinav.electronic.Dto.UpdateOrderRequestDto;
import com.abhinav.electronic.Repositories.OrderRepo;
import com.abhinav.electronic.Service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderRepo orderRepo;
	
	//create
	@PreAuthorize("hasAnyRole('"+AppConstants.ROLE_ADMIN+"','"+AppConstants.ROLE_NORMAL+"')")
	@PostMapping
	public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest request)
	{
		OrderDto order = orderService.createOrder(request);
		return new ResponseEntity<>(order,HttpStatus.OK);
		
	}
	
	//remove order
	@PreAuthorize("hasRole('"+AppConstants.ROLE_ADMIN+"')")
	@DeleteMapping("/{orderId}")
	public ResponseEntity<ApiResponseMessage> removeOrder(@PathVariable String orderId)
	{
		orderService.removOrder(orderId);
		ApiResponseMessage responseMessage =ApiResponseMessage.builder()
				                                              .status(HttpStatus.OK)
				                                              .message("order is removed")
				                                              .success(true)
				                                              .build();
		
		return new ResponseEntity<>(responseMessage,HttpStatus.OK);
		
	}
	
	//get orders of the user
	@PreAuthorize("hasAnyRole('"+AppConstants.ROLE_ADMIN+"','"+AppConstants.ROLE_NORMAL+"')")
	@GetMapping("/users/{userId}")
	public ResponseEntity<List<OrderDto>> getOrdersOfUser(@PathVariable String userId)
	{
		List<OrderDto> orderDtos = orderService.getOrdersOfUser(userId);
		return new ResponseEntity<>(orderDtos,HttpStatus.OK);
	}
	
	
	@PreAuthorize("hasRole('"+AppConstants.ROLE_ADMIN+"')")
	@GetMapping
	public ResponseEntity<PagebleResponse<OrderDto>> getOrders(
			@RequestParam(value = "pageNumber",defaultValue = "0",required = false)int pageNumber,
    		@RequestParam(value = "pageSize",defaultValue = "5",required = false)int pageSize,
    		@RequestParam(value = "sortBy",defaultValue = "orderedDate",required = false)String sortBy,
    		@RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
    		)
	{
	    PagebleResponse<OrderDto> pagebleResponse  = orderService.getOrders(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<>(pagebleResponse,HttpStatus.OK);
			
	}
	
	//update order from vendores or admin side
	@PreAuthorize("hasRole('"+AppConstants.ROLE_ADMIN+"')")
	@PutMapping("/{orderId}")
	public  ResponseEntity<OrderDto> updateOrderStatus(@PathVariable String orderId,@RequestBody UpdateOrderRequestDto updateOrderRequestDto) {
		 
	    OrderDto updatedOrder = orderService.updateOrder(orderId, updateOrderRequestDto);
		
		
		return new ResponseEntity<>(updatedOrder,HttpStatus.OK);
	}
	          
	 

}
