package com.abhinav.electronic.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhinav.electronic.Entities.Order;

public interface OrderItemRepo extends JpaRepository<Order,Integer> {

}
