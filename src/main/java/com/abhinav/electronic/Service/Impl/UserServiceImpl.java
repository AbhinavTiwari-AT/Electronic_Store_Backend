 package com.abhinav.electronic.Service.Impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.abhinav.electronic.Dto.PagebleResponse;
import com.abhinav.electronic.Dto.UserDto;
import com.abhinav.electronic.Entities.Role;
import com.abhinav.electronic.Entities.User;
import com.abhinav.electronic.Exception.ResourceNotFoundException;
import com.abhinav.electronic.Helper.Helper;
import com.abhinav.electronic.Repositories.RoleRepo;
import com.abhinav.electronic.Repositories.UserRepo;
import com.abhinav.electronic.Service.UserService;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Value("${user.profile.image.path}")
	private String imagePath;
	
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		
		//genrating user id ranodmly
		
	    String userID	= UUID.randomUUID().toString();
	                      userDto.setUserId(userID);
	    
		// dto-> entity
		User user = this.modelMapper.map(userDto , User.class);
		
		//password encoded
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		//get the normal role
		Role role = new Role();
		role.setRoleId(UUID.randomUUID().toString());
		Role roleNormal = roleRepo.findByName("ROLE_NORMAL").orElse(role);
		user.setRoles(List.of(roleNormal));
		
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
         user.setPassword(passwordEncoder.encode(userDto.getPassword()));
         user.setImageName(userDto.getImageName());
         
         //assign normal role to user
         //by default jo bhi api se user banega usko humlog normal user banyen ge
         
         
       User updatedUser = this.userRepo.save(user);
        
		return this.modelMapper.map(updatedUser,UserDto.class);
	}

	@Override
	public void deleteUser(String userId) {

		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","userId",userId));
		
		// DELETE USER PROFILE IMAGE
		// image/user/abc.png
	    String fullPath =	imagePath + user.getImageName();
	    	    
	    try {
	    	
		    Path path = Paths.get(fullPath);

			Files.delete(path);
		} 
	    
	    catch (NoSuchFileException ex) 
	    {
            logger.info("User image not found in folder");
			ex.printStackTrace();

		} catch (IOException e) {
                
			e.printStackTrace();
		}
	    
		//DELETE USER
		
		this.userRepo.delete(user);	
		          
	}

	@Override
	public PagebleResponse<UserDto> getAllUser(Integer pageNumber,Integer pageSize,String sortBy, String sortDir) {
		
		//int PageSize=5;
		//int pageNumber=1;
		
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

		Pageable p = PageRequest.of(pageNumber, pageSize,sort);
				
        Page<User> userPage = this.userRepo.findAll(p);
        PagebleResponse<UserDto> response  = Helper.getPagebleResponse(userPage,UserDto.class);
	   
		return response;
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
