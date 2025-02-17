package com.abhinav.electronic.Service.Impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.abhinav.electronic.Dto.UserDto;
import com.abhinav.electronic.Entities.User;
import com.abhinav.electronic.Exception.ResourceNotFoundException;
import com.abhinav.electronic.Repositories.UserRepo;
import com.abhinav.electronic.Service.UserService;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto userDto) {
		
		//genrating user id ranodmly
		
	    String userID	= UUID.randomUUID().toString();
	                      userDto.setUserId(userID);
	    
		
		User user = this.modelMapper.map(userDto , User.class);
		User createUser = this.userRepo.save(user);
		return this.modelMapper.map(createUser,UserDto.class);
	}

	@Override
	public UserDto updateUser(UserDto userDto, String userId) {

		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","userId",userId));
         user.setName(userDto.getName());
         user.setAbout(userDto.getAbout());
         user.setGender(userDto.getGender());
     //  user.setEmail(userDto.getEmail());
         user.setPassword(userDto.getPassword());
         user.setImageName(userDto.getImageName());
         
       User updatedUser = this.userRepo.save(user);
        
		return this.modelMapper.map(updatedUser,UserDto.class);
	}

	@Override
	public void deleteUser(String userId) {

		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","userId",userId));
		             this.userRepo.delete(user);	}

	@Override
	public List<UserDto> getAllUser(Integer pageNumber,Integer pageSize) {
		
		//int PageSize=5;
		//int pageNumber=1;

		Pageable p = PageRequest.of(pageNumber, pageSize);
				
        Page<User> userPage = this.userRepo.findAll(p);
        List<User> users = userPage.getContent();
	    List<UserDto> userDtos =users.stream().map(user -> this.modelMapper.map(user,UserDto.class)).collect(Collectors.toList());		
		
		return userDtos;
	}

	@Override
	public UserDto getUserById(String userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","userId",userId));
	
		return this.modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto getUserByEmail(String email) {

		User user= userRepo.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User","Useremail",email));
		
		return this.modelMapper.map(user,UserDto.class);
		
	}

	@Override
	public List<UserDto> searchUser(String keyword) {

		List<User> users = userRepo.findByNameContaining(keyword);
		List<UserDto> dtoList = users.stream().map(user -> this.modelMapper.map(user,UserDto.class)).collect(Collectors.toList());
		return dtoList;
	}

}
