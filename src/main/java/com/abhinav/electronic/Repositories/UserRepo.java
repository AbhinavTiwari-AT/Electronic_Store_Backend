package com.abhinav.electronic.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhinav.electronic.Entities.User;

public interface UserRepo extends JpaRepository<User, String>
{
  
}
