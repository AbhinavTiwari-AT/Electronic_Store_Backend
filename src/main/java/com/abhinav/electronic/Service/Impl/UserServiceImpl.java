package com.abhinav.electronic.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhinav.electronic.Dto.UserDto;
import com.abhinav.electronic.Entities.User;
import com.abhinav.electronic.Repositories.UserRepo;
import com.abhinav.electronic.Service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDto createUser(UserDto userDto) {

		
		return null;
	}

	@Override
	public UserDto updateUser(UserDto userDto, String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDto deleteUser(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDto> getAllUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDto getUserById(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDto getUserByEmail(String email) {

		User user= userRepo.findByEmail(email).orElseThrow(()-> new RuntimeException("Email not found"));
		
		return entityToDto(user);
	}

	@Override
	public List<UserDto> searchUser(String keyword) {

		List<User> users = userRepo.findByNameContaining(keyword);
		List<UserDto> dtoList = users.stream().map(users -> entityToDto(user))
				
		
		return null;
	}

}
