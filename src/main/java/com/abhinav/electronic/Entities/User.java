package com.abhinav.electronic.Entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
public class User implements UserDetails {
	
	
	//ADMIN
	//NORMAL
	
	@Id
	private String userId;
	
	@Column(name = "user_name")
	private String name;
	
	@Column(name = "user_email",unique = true)
	private String email;
	
	@Column(name = "user_password",length = 255)
	private String password;
	
	@Column(name = "user_gender")
	private String gender;
	
	@Column(name = "about", length = 1000)
	private String about;
	
	@Column(name = "user_image_name")
	private String imageName;
	
	@OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
	private List<Order> orders =new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)	
	private List<Role> roles = new ArrayList<>();
	
	
	// important to manage role
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Set<SimpleGrantedAuthority> authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
		return authorities;
	}

	@Override
	public String getUsername() {

		return this.getEmail();
	}

}
