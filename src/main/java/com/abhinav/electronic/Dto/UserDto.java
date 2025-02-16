package com.abhinav.electronic.Dto;

import com.abhinav.electronic.Validate.ImageNameValid;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
	
    private String userId;
	
    @Size(min = 3,max = 20,message = "Invalid name !!")
	private String name;
	
    @Pattern(regexp = "^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$",message = "Invalid email !!")
   // @Email(message = "Invalid user email !!")
    @NotBlank(message = "Email not be blank !!") 
	private String email;
	
    @NotBlank(message = "password is required !!")
	private String password;
	
    @Size(min = 4,max = 6,message = "Invalid gender !!")
	private String gender;
	
    @NotBlank(message = "Write Something about Yourself !!")
	private String about;
    
    //@patter
    //Custom vaildator
	
    @ImageNameValid
	private String imageName;

}
