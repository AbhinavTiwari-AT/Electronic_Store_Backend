package com.abhinav.electronic.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhinav.electronic.Entities.Order;
import com.abhinav.electronic.Entities.User;

public interface OrderRepo extends JpaRepository<Order,String>{
	
	List<Order> findByUser(User user);

 
}
