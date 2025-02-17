package com.abhinav.electronic.Service;

import java.util.List;

import com.abhinav.electronic.Dto.PagebleResponse;
import com.abhinav.electronic.Dto.UserDto;

public interface UserService {
	
	//crate
	UserDto createUser(UserDto userDto);
	
	//update
	UserDto updateUser(UserDto userDto,String userId);
	
	//delete
	void deleteUser(String userId);
	
	//get all users
	PagebleResponse<UserDto> getAllUser(Integer pageNumber,Integer pageSize,String sortBy, String sortDir);
	
	//get single user by id
	UserDto getUserById(String userId);
	
	//get single user by email
	UserDto getUserByEmail(String email);
	
	//search user
	List<UserDto> searchUser(String keyword);
	
	//other user specific features

}
