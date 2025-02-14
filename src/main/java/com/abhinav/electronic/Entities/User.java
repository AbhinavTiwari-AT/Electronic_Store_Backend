package com.abhinav.electronic.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
	
	
	@Id
	private String userId;
	
	@Column(name = "user_name")
	private String name;
	
	@Column(name = "user_email",unique = true)
	private String email;
	
	@Column(name = "user_password",length = 10)
	private String password;
	
	@Column(name = "user_gender")
	private String gender;
	
	@Column(name = "about", length = 1000)
	private String about;
	
	@Column(name = "user_image_name")
	private String imageName;

}
