package com.abhinav.electronic.Repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.abhinav.electronic.Entities.Product;

public interface ProductRepo extends JpaRepository<Product, String>{
	
	//Search 
	Page<Product> findByTitleContaining(String subtitle,Pageable pageable);
	
	Page<Product> findByLiveTrue(Pageable pageable);

}
