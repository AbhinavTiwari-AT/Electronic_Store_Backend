package com.abhinav.electronic.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.abhinav.electronic.Dto.ApiResponseMessage;
import com.abhinav.electronic.Dto.ImageResponse;
import com.abhinav.electronic.Dto.PagebleResponse;
import com.abhinav.electronic.Dto.UserDto;
import com.abhinav.electronic.Service.FileService;
import com.abhinav.electronic.Service.UserService;
import com.abhinav.electronic.Service.Impl.FileServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@Tag(name = "UserController", description = "Controller for managing users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${user.profile.image.path}")
    private String imageUploadPath;
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
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
	
	// upload user image
	
	@PostMapping("/image/{userId}")
	public ResponseEntity<ImageResponse> uploadUserImage(@PathVariable String userId , @RequestParam("userImage") MultipartFile image) throws IOException
	{
	 	 String imageName  =  fileService.uploadImage(image,imageUploadPath);
	 	 
	 	 UserDto user = userService.getUserById(userId);
	 	 
	 	 user.setImageName(imageName);
	 	 
	     UserDto userDto = userService.updateUser(user, userId);
	 	 
		 ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).message("image is created sucessfully").success(true).status(HttpStatus.CREATED).build();

		 return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
		
	}
	
	//serve user image
	
	@GetMapping("/image/{userId}")
	public void serveUserImage(@PathVariable String userId , HttpServletResponse response) throws IOException
	{
		//
	    UserDto user  = userService.getUserById(userId);
	    logger.info("User image name : {} ",user.getImageName());
		InputStream resource = fileService.getResource(imageUploadPath, user.getImageName());
		
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		
	    StreamUtils.copy(resource,response.getOutputStream());
		
		
	}

}
