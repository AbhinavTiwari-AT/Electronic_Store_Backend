package com.abhinav.electronic.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhinav.electronic.Entities.Role;

public interface RoleRepo extends JpaRepository<Role, String>{
	
	Optional<Role> findByName(String name);
	
}
