package com.abhinav.electronic.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	// security filter chain beans
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
		
		//configuration
		//urls 
		//public and producted
		//koan sa urls admin, koun se normal hai
		
		//cors ko abhi humne disable kiya hai
		security.cors(httpSecurityCorsConfiguration -> httpSecurityCorsConfiguration.disable());
		
		//csrf ka home abhi ke liye disable kiya hai
		security.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());
		
		
		// configuring urls
		security.authorizeHttpRequests(request -> 
		         request.requestMatchers(HttpMethod.DELETE,"/users/**").hasRole("ADMIN")
		                .requestMatchers(HttpMethod.PUT,"/users/**").hasAnyRole("ADMIN","NORMAL")
		                .requestMatchers(HttpMethod.GET,"/products/**").permitAll()
		                .requestMatchers("/products/**").hasRole("ADMIN")
		                .requestMatchers(HttpMethod.GET,"/users/**").permitAll()
		                .requestMatchers(HttpMethod.POST,"/users").permitAll()
   		                .requestMatchers(HttpMethod.GET,"/categories/**").permitAll()
   		                .requestMatchers("/categories/**").hasRole("ADMIN")
   		                .anyRequest().permitAll()
   		         
				);
		
		//kis type ki security: basic with default configuration 
		security.httpBasic(Customizer.withDefaults());
		
		return security.build();
		
	}
	
	// password encoder
	@Bean
	public PasswordEncoder passwordEncoder() {
	
		return new BCryptPasswordEncoder();
		
	}
	

}
