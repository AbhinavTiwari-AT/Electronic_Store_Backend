package com.abhinav.electronic.Controller;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abhinav.electronic.Dto.JwtRequest;
import com.abhinav.electronic.Dto.JwtResponse;
import com.abhinav.electronic.Dto.UserDto;
import com.abhinav.electronic.Entities.User;
import com.abhinav.electronic.Security.JwtHelper;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	//method to generate token;
	
	private Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtHelper helper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping("/generate-token")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request)
	{
		logger.info("Username {} , Password {}",request.getEmail(),request.getPassword());
		
		this.doAuthenticate(request.getEmail(),request.getPassword());
		
		 User user = (User)userDetailsService.loadUserByUsername(request.getEmail());
		
		 ///.. generate token...
		 String token = helper.generateToken(user);
		//send karna hai response
		
		 JwtResponse jwtResponse = JwtResponse.builder().token(token).user(modelMapper.map(user, UserDto.class)).build();
		
		return ResponseEntity.ok(jwtResponse);
		
	}

	private void doAuthenticate(String username, String password) {

		try {
			Authentication authentication = new UsernamePasswordAuthenticationToken(username,password);
			authenticationManager.authenticate(authentication);
			
		} catch (BadCredentialsException ex) {
			
			throw new BadCredentialsException("Invalid Username and Password !!");
		}
		
	}
}
