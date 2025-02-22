package com.abhinav.electronic.Service;

import com.abhinav.electronic.Dto.CategoryDto;
import com.abhinav.electronic.Dto.PagebleResponse;

public interface CategoryService {
	
	//create
    CategoryDto createCategory(CategoryDto categoryDto);
	
	//update
    CategoryDto updateCategory(CategoryDto categoryDto,String categoryId);
	
	//delete
    void delete(String categoryId);
	
	//get all
    PagebleResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy , String sortDir);
	
	//get single category detail
    CategoryDto get(String categoryId);
	
	//search: 
}
