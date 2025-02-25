package com.abhinav.electronic.Service.Impl;


import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhinav.electronic.Dto.AddItemToCartRequest;
import com.abhinav.electronic.Dto.CartDto;
import com.abhinav.electronic.Entities.Cart;
import com.abhinav.electronic.Entities.CartItem;
import com.abhinav.electronic.Entities.Product;
import com.abhinav.electronic.Entities.User;
import com.abhinav.electronic.Exception.BadApiRequest;
import com.abhinav.electronic.Exception.ResourceNotFoundException;
import com.abhinav.electronic.Repositories.CartItemRepo;
import com.abhinav.electronic.Repositories.CartRepo;
import com.abhinav.electronic.Repositories.ProductRepo;
import com.abhinav.electronic.Repositories.UserRepo;
import com.abhinav.electronic.Service.CartService;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CartRepo cartRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CartItemRepo cartItemRepo;

	@Override
	public CartDto addItemTOCart(String userId, AddItemToCartRequest request) {

		int quantity = request.getQuantity();
		String productId = request.getProductId();
		
		if (quantity <= 0) {
			
			throw new BadApiRequest("Request quantity is not valid!!!");
			
		}
		
		//fetch the product
		Product product = this.productRepo.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product","productId",productId));
		
		//fetch the user 
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","userId", userId));
		
		Cart cart = null;
		try {
			
		  cart = this.cartRepo.findByUser(user).get();

		} catch (NoSuchElementException e) {
			
			cart = new Cart();
			cart.setCartId(UUID.randomUUID().toString());
			cart.setCreatedAt(new Date());
		}
		
		//perform cart operation
		//if cart items already present; then update
	     AtomicReference<Boolean> updated = new AtomicReference<>(false);		
	    List<CartItem> items = cart.getItems();
	    
	    List<CartItem> updatedItems = items.stream().map(item -> {
	    	
	    	if (item.getProduct().getProductId().equals(productId)) {
	    		
	    		//item already present	
	    		item.setQuantity(quantity);
	    		item.setTotalPrice(quantity*product.getPrice());
	    		updated.set(true);
			}
	    	return item;
	    }).collect(Collectors.toList());
	    
	    cart.setItems(updatedItems);
		
		//create items
	   if (!updated.get()) {
		   CartItem cartItem = CartItem.builder().quantity(quantity).totalPrice(quantity*product.getPrice())
		             .cart(cart)
		             .product(product)
		             .build();
		   cart.getItems().add(cartItem); 
	}
		
		cart.setUser(user);
		Cart updatedCart  = cartRepo.save(cart);
		
		return modelMapper.map(updatedCart,CartDto.class);
	}

	@Override
	public void removeItemFromCart(String userId, int cartItem) {

		//conditions
		CartItem cartItem1  = cartItemRepo.findById(cartItem).orElseThrow(()-> new ResourceNotFoundException("CartItem","cartItemId", cartItem));
		cartItemRepo.delete(cartItem1);
	}

	@Override
	public void clearCart(String userId) {
		//fetch user for db
		User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","userId", userId));
        Cart cart = cartRepo.findByUser(user).orElseThrow(()-> new ResourceNotFoundException("User","userId", user));
        cart.getItems().clear();
        cartRepo.save(cart);
	}

	@Override
	public CartDto getCartByUser(String userId) {
	
		User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","userId", userId));
        Cart cart = cartRepo.findByUser(user).orElseThrow(()-> new ResourceNotFoundException("User","userId", user));
		return modelMapper.map(cart,CartDto.class);
	}

}
