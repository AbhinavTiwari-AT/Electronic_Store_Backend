package com.abhinav.electronic.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abhinav.electronic.Dto.ApiResponseMessage;
import com.abhinav.electronic.Dto.PagebleResponse;
import com.abhinav.electronic.Dto.UserDto;
import com.abhinav.electronic.Service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//create
	@PostMapping
	public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto)
	{ 
		UserDto created = this.userService.createUser(userDto);
	    return new ResponseEntity<>(created,HttpStatus.CREATED);
		
	}
	
	//update
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable String userId)
	{
		UserDto update = this.userService.updateUser(userDto, userId);
		return new ResponseEntity<>(update,HttpStatus.ACCEPTED);
	}
	
	//delete
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponseMessage>deleteUser(@PathVariable String userId) 
	{
		this.userService.deleteUser(userId);
	    ApiResponseMessage message = ApiResponseMessage
	    		                    .builder()
	    		                    .message("User is deleted Sucessfully !!")
	    		                    .success(true)
	    		                    .status(HttpStatus.OK)
	    		                    .build();
		return  new ResponseEntity<>(message,HttpStatus.OK);
		
	}
	
	//get all
	@GetMapping
	public ResponseEntity<PagebleResponse<UserDto>> getAllUser
	        (
			@RequestParam(value = "pageNumber",defaultValue = "0",required = false)Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = "5",required = false) Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue = "name",required = false)String sortBy,
			@RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
			)
	   
	{
		// pageNumber = pageNumber - 1;

		PagebleResponse<UserDto> getDto = this.userService.getAllUser(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<>(getDto,HttpStatus.OK);
		
	}
	
	//get single user by id
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto>getUserById(@PathVariable String userId)
	{
		UserDto getUser = this.userService.getUserById(userId);
		return new ResponseEntity<>(getUser,HttpStatus.FOUND);
	}
	
	//get by email
	@GetMapping("/email/{email}")
	public ResponseEntity<UserDto>getUserByEmail(@PathVariable String email){
		
		UserDto getUser = this.userService.getUserByEmail(email);
		return new ResponseEntity<>(getUser,HttpStatus.FOUND);
	}
	
	//search user
	@GetMapping("/search/{keywords}")
	public ResponseEntity<List<UserDto>>searchUser(@PathVariable String keywords){
		
		List<UserDto> getUser = this.userService.searchUser(keywords);
		return new ResponseEntity<>(getUser,HttpStatus.FOUND);
	}

}
