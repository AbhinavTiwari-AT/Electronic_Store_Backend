package com.abhinav.electronic.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "categories")
public class Category {
	
	@Id
	@Column(name = "id")
	private String categoryId;
	
	@Column(name = "category_title",length = 60,nullable = false)
	private String title;
	
	@Column(name = "category_desc",length = 60)
	private String description;
	
	private String coverImage;
	
	//other attributes if you have....
}
