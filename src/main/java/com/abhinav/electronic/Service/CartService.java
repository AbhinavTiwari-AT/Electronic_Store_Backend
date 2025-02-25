package com.abhinav.electronic.Service;

import com.abhinav.electronic.Dto.AddItemToCartRequest;
import com.abhinav.electronic.Dto.CartDto;

public interface CartService {
	
	//add items to cart:
	//case:1 cart for user is not available: we will create the cart and then add the item
	//case:2 cart avilable add the items to cart
	
	CartDto addItemToCart(String userId,AddItemToCartRequest request);
	
	//remove item from cart:
	void removeItemFromCart(String userId,int cartItem);
	
	//remove all items from cart
	void clearCart(String userId);
	
	CartDto getCartByUser(String userId);
	

}
