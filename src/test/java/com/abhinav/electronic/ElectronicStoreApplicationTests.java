package com.abhinav.electronic;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.abhinav.electronic.Entities.User;
import com.abhinav.electronic.Repositories.UserRepo;
import com.abhinav.electronic.Security.JwtHelper;


@SpringBootTest
class ElectronicStoreApplicationTests {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private JwtHelper jwtHelper;

	@Test
	void contextLoads() {
	}

	@Test
	void testToken()
	{
		User user = userRepo.findByEmail("abhinav@gmail.com").get();
		
		String token = jwtHelper.generateToken(user);
		System.out.println(token);
		
		System.out.println("getting username from token");
		System.out.println(jwtHelper.getUsernameFromToken(token));
		
		System.out.println("is token expired");
		System.out.println(jwtHelper.isTokenExpired(token));
	}
	
	
}
