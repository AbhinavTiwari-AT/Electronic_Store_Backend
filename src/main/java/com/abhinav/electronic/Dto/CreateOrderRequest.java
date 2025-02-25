package com.abhinav.electronic.Dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CreateOrderRequest {
	
	
	private String cartId;
	
	private String userId;
	
	private String orderStatus= "PENDING";
	
	private String paymentStatus = "NOTPAID";
	
	private  String billingName;
	
	private String billingAddress;
	
	private String billingPhone;
			
	

}
