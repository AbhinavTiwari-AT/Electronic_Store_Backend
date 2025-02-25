package com.abhinav.electronic.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abhinav.electronic.Dto.AddItemToCartRequest;
import com.abhinav.electronic.Dto.ApiResponseMessage;
import com.abhinav.electronic.Dto.CartDto;
import com.abhinav.electronic.Service.CartService;

@RestController
@RequestMapping("/carts")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	//add items to cart
	@PostMapping("/{userId}")
	public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId, @RequestBody AddItemToCartRequest request) {
		CartDto cartDto  = cartService.addItemToCart(userId,request);
		return new ResponseEntity<>(cartDto,HttpStatus.OK);
	}
	
	@DeleteMapping("/{userId}/items/{itemId}")
	public ResponseEntity<ApiResponseMessage> removeItemFromCart(@PathVariable String userId,@PathVariable int itemId){
		
		 cartService.removeItemFromCart(userId, itemId);
		 ApiResponseMessage response = ApiResponseMessage.builder().message("Item is removed !!").success(true).status(HttpStatus.OK).build();
		 return new ResponseEntity<>(response,HttpStatus.OK);	
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponseMessage> clearCart(@PathVariable String userId){
		
		 cartService.clearCart(userId);
		 ApiResponseMessage response = ApiResponseMessage.builder().message("Cart is blank !!").success(true).status(HttpStatus.OK).build();
		 return new ResponseEntity<>(response,HttpStatus.OK);	
	}
	
	//get cart
	@GetMapping("/{userId}")
	public ResponseEntity<CartDto> getCart(@PathVariable String userId) {
	   CartDto cartDto  = cartService.getCartByUser(userId);
	   return new ResponseEntity<>(cartDto,HttpStatus.OK);
	}
		
	
}
