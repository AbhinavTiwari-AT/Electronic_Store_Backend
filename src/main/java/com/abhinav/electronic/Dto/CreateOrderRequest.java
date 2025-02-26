package com.abhinav.electronic.Dto;



import jakarta.validation.constraints.NotBlank;
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
	
	@NotBlank(message = "cart id is required !!")
	private String cartId;
	
	@NotBlank(message = "user id is required !!")
	private String userId;
	
	private String orderStatus= "PENDING";
	
	private String paymentStatus = "NOTPAID";
	
	@NotBlank(message = "Name is required !!")
	private  String billingName;
	
	@NotBlank(message = "Address is required !!")
	private String billingAddress;
	
	@NotBlank(message = "phone number is required !!")
	private String billingPhone;
			
	

}
