package com.abhinav.electronic.Dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderRequestDto {
	
	
	  private String orderStatus;
		
	  private String paymentStatus;
	  
	  private Date deliveredDate;


}
