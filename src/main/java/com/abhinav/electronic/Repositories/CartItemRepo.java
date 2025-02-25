package com.abhinav.electronic.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhinav.electronic.Entities.CartItem;

public interface CartItemRepo extends JpaRepository<CartItem,Integer> {

}
