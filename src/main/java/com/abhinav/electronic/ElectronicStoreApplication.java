package com.abhinav.electronic;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.abhinav.electronic.Entities.Role;
import com.abhinav.electronic.Entities.User;
import com.abhinav.electronic.Repositories.RoleRepo;
import com.abhinav.electronic.Repositories.UserRepo;

@SpringBootApplication
public class ElectronicStoreApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ElectronicStoreApplication.class, args);
	}

	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void run(String... args) throws Exception {
		

		Role roleAdmin  = roleRepo.findByName("ROLE_ADMIN").orElse(null);
		
		if(roleAdmin == null)
		{
			Role role1= new Role();
			role1.setRoleId(UUID.randomUUID().toString());
			role1.setName("ROLE_ADMIN");
			roleRepo.save(role1);
		}
		
		Role roleNormal  = roleRepo.findByName("ROLE_NORMAL").orElse(null);

		
		if(roleNormal == null)
		{
			Role role2 = new Role();
			role2.setRoleId( UUID.randomUUID().toString());
			role2.setName("ROLE_NORMAL");
			roleRepo.save(role2);
		}
		
		//ek admin user banauga
		User user = userRepo.findByEmail("abhinav@gmail.com").orElse(null);
		if (user == null) 
		{
			user = new User();
			user.setName("abhinav");
			user.setEmail("abhinav@gmail.com");
			user.setPassword(passwordEncoder.encode("abhinav"));
			user.setRoles(List.of(roleAdmin));
			user.setUserId(UUID.randomUUID().toString());
			
			userRepo.save(user);
			
		}
		
	}

	
}
