package com.abhinav.electronic.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhinav.electronic.Entities.User;

public interface UserRepo extends JpaRepository<User, String>

{
    Optional<User> findByEmail(String email);
    
    Optional<User> findByEmailAndPassword(String email,String password);
    
    List<User> findByNameContaining(String keywords);
    
}
