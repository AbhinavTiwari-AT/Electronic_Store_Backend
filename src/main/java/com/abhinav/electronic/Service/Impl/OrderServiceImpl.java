package com.abhinav.electronic.Service.Impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.abhinav.electronic.Dto.CreateOrderRequest;
import com.abhinav.electronic.Dto.OrderDto;
import com.abhinav.electronic.Dto.PagebleResponse;
import com.abhinav.electronic.Entities.Cart;
import com.abhinav.electronic.Entities.CartItem;
import com.abhinav.electronic.Entities.Order;
import com.abhinav.electronic.Entities.OrderItem;
import com.abhinav.electronic.Entities.User;
import com.abhinav.electronic.Exception.BadApiRequest;
import com.abhinav.electronic.Exception.ResourceNotFoundException;
import com.abhinav.electronic.Helper.Helper;
import com.abhinav.electronic.Repositories.CartRepo;
import com.abhinav.electronic.Repositories.OrderRepo;
import com.abhinav.electronic.Repositories.UserRepo;
import com.abhinav.electronic.Service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private OrderRepo orderRepo;
	
	@Autowired
	private CartRepo cartRepo;
	
	
	@Override
	public OrderDto createOrder(CreateOrderRequest orderDto) {
		
		String userId = orderDto.getUserId();
		String cartId = orderDto.getCartId();
		
		//fetch user
		User user  = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","userId", userId));
		
		//fetch cart
		Cart cart = this.cartRepo.findById(cartId).orElseThrow(()-> new ResourceNotFoundException("Cart", "cartId", cartId));
		
		List<CartItem> cartItems  = cart.getItems();
		
		if (cartItems.size()<=0) {
			
			throw new BadApiRequest("Invalid number of items in cart !!");
			
		}
		
		//other checks
		Order order = Order.builder().billingName(orderDto.getBillingName())
		               .billingPhone(orderDto.getBillingPhone())
		               .billingAddress(orderDto.getBillingAddress())
		               .orderedDate(new Date())
		               .deliveredDate(null)
		               .paymentStatus(orderDto.getPaymentStatus())
		               .orderStatus(orderDto.getOrderStatus())
		               .orderId(UUID.randomUUID().toString())
		               .user(user)
		               .build();
		//orderitem and amount
		AtomicReference<Integer> orderAmount = new AtomicReference<>(0);
		List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
			
			//	CartItem -> OrderItem
			OrderItem orderItem = OrderItem.builder()
			          .quantity(cartItem.getQuantity())
			          .product(cartItem.getProduct())
			          .totalPrice(cartItem.getQuantity()*cartItem.getProduct().getDiscountedPrice())
			          .order(order)
			          .build();
	
			return orderItem;
			
		}).collect(Collectors.toList());
		
		order.setOrderItems(orderItems);
		order.setOrderAmount(orderAmount.get());
		
		//
		cart.getItems().clear();
		cartRepo.save(cart);
		Order savedOrder = orderRepo.save(order);
		
		return modelMapper.map(savedOrder,OrderDto.class);
	}

	
	@Override
	public void removOrder(String orderId) {
		
	Order order = this.orderRepo.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order","orderId", orderId));
	orderRepo.delete(order);
	
	}

	@Override
	public List<OrderDto> getOrdersOfUser(String userId) {
	    
	  User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","userId", userId));
	  List<Order> orders = orderRepo.findByUser(user);
	  List<OrderDto> orderDtos = orders.stream().map(order -> modelMapper.map(order,OrderDto.class)).collect(Collectors.toList());
		
		return orderDtos;
	}

	@Override
	public PagebleResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {

		Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pagable = PageRequest.of(pageNumber, pageSize,sort);
        Page<Order> page= this.orderRepo.findAll(pagable);
		return Helper.getPagebleResponse(page,OrderDto.class);
	}
	
	

}
