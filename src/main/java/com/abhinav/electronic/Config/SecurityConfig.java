package com.abhinav.electronic.Config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.abhinav.electronic.Security.JwtAuthenticationEntryPoint;
import com.abhinav.electronic.Security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	
	@Autowired
	private JwtAuthenticationFilter filter;
	
	@Autowired
	private JwtAuthenticationEntryPoint entryPoint;
	
	
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
		         request.requestMatchers(HttpMethod.DELETE,"/users/**").hasRole(AppConstants.ROLE_ADMIN)
		                .requestMatchers(HttpMethod.PUT,"/users/**").hasAnyRole(AppConstants.ROLE_ADMIN,AppConstants.ROLE_NORMAL)
		                .requestMatchers(HttpMethod.GET,"/products/**").permitAll()
		                .requestMatchers("/products/**").hasRole(AppConstants.ROLE_ADMIN)
		                .requestMatchers(HttpMethod.GET,"/users/**").permitAll()
		                .requestMatchers(HttpMethod.POST,"/users").permitAll()
   		                .requestMatchers(HttpMethod.GET,"/categories/**").permitAll()
   		                .requestMatchers("/categories/**").hasRole(AppConstants.ROLE_ADMIN)
   		                .requestMatchers(HttpMethod.POST,"/auth/generate-token").permitAll()
   		                .requestMatchers("/auth/**").authenticated()
   		                .anyRequest().permitAll()
   		         
				);
		
		//kis type ki security: basic with default configuration 
	
		// security.httpBasic(Customizer.withDefaults());
		
		
		// for jwt configuration entry point
		security.exceptionHandling(ex -> ex.authenticationEntryPoint(entryPoint));
		
		//session creation policy
		security.sessionManagement(sessio -> sessio.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		//main ->
		security.addFilterBefore(filter,UsernamePasswordAuthenticationFilter.class);
		
		return security.build();
		
	}
	
	// password encoder
	@Bean
	public PasswordEncoder passwordEncoder() {
	
		return new BCryptPasswordEncoder();
		
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
		
		return builder.getAuthenticationManager();
	}
	

}
