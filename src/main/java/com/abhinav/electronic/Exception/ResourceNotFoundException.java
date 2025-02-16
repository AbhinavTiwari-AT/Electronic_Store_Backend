package com.abhinav.electronic.Exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException
{
	
	String resourceName;
	String fieldName;
	String fieldValue;
	
	
	
	public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
	    super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue));
	}
	

}
