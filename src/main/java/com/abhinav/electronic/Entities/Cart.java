package com.abhinav.electronic.Entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cart")
public class Cart 
{
	@Id
	private String cartId;
	
	private Date createdAt;
	
	@OneToOne
	@JoinColumn(name = "user_Id")
	private User user;
	
	//mapping cart-items
	@OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
	private List<CartItem> items = new ArrayList<>();

}
