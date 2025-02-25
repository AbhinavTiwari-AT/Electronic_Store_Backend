package com.abhinav.electronic.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhinav.electronic.Entities.Cart;
import com.abhinav.electronic.Entities.User;

public interface CartRepo extends JpaRepository<Cart, String>{
	
	Optional<Cart> findByUser(User user);

}
