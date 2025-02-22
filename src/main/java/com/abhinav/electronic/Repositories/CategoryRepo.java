package com.abhinav.electronic.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhinav.electronic.Entities.Category;

public interface CategoryRepo extends JpaRepository<Category, String> {

}
