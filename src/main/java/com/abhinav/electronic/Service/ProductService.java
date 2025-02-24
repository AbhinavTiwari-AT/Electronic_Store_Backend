package com.abhinav.electronic.Service;


import com.abhinav.electronic.Dto.PagebleResponse;
import com.abhinav.electronic.Dto.ProductDto;

public interface ProductService {
	
	
	//create
	ProductDto createProduct(ProductDto productDto);
	
	//update
	ProductDto updateProduct(ProductDto productDto,String productId);
	
	//delete
    void deleteProduct(String productId);
	
	//get single
    ProductDto getProductById(String productId);
	
	//get all
    PagebleResponse<ProductDto> getAll(Integer pageNumber,Integer pageSize,String sortBy, String sortDir);
	
	//get all : live
    PagebleResponse<ProductDto> getAllLive(Integer pageNumber,Integer pageSize,String sortBy, String sortDir);
	
	//search product
    PagebleResponse<ProductDto> searchByTitle(String subTitle,Integer pageNumber,Integer pageSize,String sortBy, String sortDir);
	
	//create product with category
    ProductDto createProductwithCategory(ProductDto productDto, String categoryId);
    
    
    //update category of product
    ProductDto updateCategory(String productId, String categoryId);
    
    PagebleResponse<ProductDto> getAllofCategory(String categoryId,int pageNumber,int pageSize,String sortBy,String sortDir);
    
}
