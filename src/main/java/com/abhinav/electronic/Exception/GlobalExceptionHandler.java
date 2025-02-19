package com.abhinav.electronic.Exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.abhinav.electronic.Dto.ApiResponseMessage;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	
	private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	//handle resource not found exception
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponseMessage> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
		
		logger.info("Exception Handler invoked !!");
	    ApiResponseMessage responseMessage = ApiResponseMessage.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(true).build();
		return new ResponseEntity<>(responseMessage,HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
      public ResponseEntity<Map<String,Object>> handleMethodArgumentNotvalidException(MethodArgumentNotValidException ex){
    	 
    	  List<ObjectError> allErrors  = ex.getBindingResult().getAllErrors();
    	  Map<String, Object> responseMap = new HashMap<>();
    	  allErrors.stream().forEach(ObjectError -> {
    		  String message = ObjectError.getDefaultMessage();
    		  String field = ((FieldError) ObjectError).getField();
    		  responseMap.put(field,message);
    	  });
    	  
	     return new ResponseEntity<>(responseMap,HttpStatus.BAD_REQUEST);
    	  
      }
	
	// handle bad api exception
	
	@ExceptionHandler(BadApiRequest.class)
	public ResponseEntity<ApiResponseMessage> handleBadApiRequest(BadApiRequest ex){
		
		logger.info("Bad Api Request !!");
	    ApiResponseMessage responseMessage = ApiResponseMessage.builder().message(ex.getMessage()).status(HttpStatus.BAD_REQUEST).success(false).build();
		return new ResponseEntity<>(responseMessage,HttpStatus.BAD_REQUEST);
		
	}
}
