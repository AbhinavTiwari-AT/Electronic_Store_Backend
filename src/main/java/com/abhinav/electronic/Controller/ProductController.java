package com.abhinav.electronic.Controller;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abhinav.electronic.Dto.ApiResponseMessage;
import com.abhinav.electronic.Dto.PagebleResponse;
import com.abhinav.electronic.Dto.ProductDto;
import com.abhinav.electronic.Service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	//create
	@PostMapping
	public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
		
	
		ProductDto created  =  this.productService.createProduct(productDto);
		
		return new ResponseEntity<>(created,HttpStatus.CREATED);
		
	}
	
	//update
	@PutMapping("/{productId}")
	public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, @PathVariable String productId){
		
		ProductDto updated  = this.productService.updateProduct(productDto, productId);
		return new ResponseEntity<>(updated,HttpStatus.OK);
	}
	
	//delete
	@DeleteMapping("/{productId}")
	public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable String productId){
		
		productService.deleteProduct(productId);
		ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("Product is Deleted Sucessfully !!").status(HttpStatus.OK).success(true).build();
		return new ResponseEntity<>(responseMessage,HttpStatus.OK);
	}
	
	//get single
	@GetMapping("/{productId}")
	public ResponseEntity<ProductDto> getProduct(@PathVariable String productId){
		
		ProductDto productDto  =  this.productService.getProductById(productId);
		
		return new ResponseEntity<>(productDto,HttpStatus.OK);
		
	}
	
	
	//get all
	@GetMapping
	public ResponseEntity<PagebleResponse<ProductDto>> getAll(
				@RequestParam(value = "pageNumber",defaultValue = "0",required = false)int pageNumber,
	    		@RequestParam(value = "pageSize",defaultValue = "5",required = false)int pageSize,
	    		@RequestParam(value = "sortBy",defaultValue = "title",required = false)String sortBy,
	    		@RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
	    		)
		{
		    PagebleResponse<ProductDto> pagebleResponse  = productService.getAll(pageNumber, pageSize, sortBy, sortDir);
			return new ResponseEntity<>(pagebleResponse,HttpStatus.OK);
			
		}
	
	
	//get all live
	@GetMapping("/live")
	public ResponseEntity<PagebleResponse<ProductDto>> getAllLive(
				@RequestParam(value = "pageNumber",defaultValue = "0",required = false)int pageNumber,
	    		@RequestParam(value = "pageSize",defaultValue = "5",required = false)int pageSize,
	    		@RequestParam(value = "sortBy",defaultValue = "title",required = false)String sortBy,
	    		@RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
	    		)
		{
		    PagebleResponse<ProductDto> pagebleResponse  = productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
			return new ResponseEntity<>(pagebleResponse,HttpStatus.OK);
			
		}
	
	
	//search all
	@GetMapping("/search/{query}")
	public ResponseEntity<PagebleResponse<ProductDto>> searchProduct(
			    @PathVariable String query,
				@RequestParam(value = "pageNumber",defaultValue = "0",required = false)int pageNumber,
	    		@RequestParam(value = "pageSize",defaultValue = "5",required = false)int pageSize,
	    		@RequestParam(value = "sortBy",defaultValue = "title",required = false)String sortBy,
	    		@RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
	    		)
		{
		    PagebleResponse<ProductDto> pagebleResponse  = productService.searchByTitle(query,pageNumber, pageSize, sortBy, sortDir);
			return new ResponseEntity<>(pagebleResponse,HttpStatus.OK);
			
		}
	
	

}
