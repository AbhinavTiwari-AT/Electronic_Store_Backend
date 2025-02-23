package com.abhinav.electronic.Service.Impl;


import java.util.Date;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.abhinav.electronic.Dto.PagebleResponse;
import com.abhinav.electronic.Dto.ProductDto;
import com.abhinav.electronic.Entities.Product;
import com.abhinav.electronic.Exception.ResourceNotFoundException;
import com.abhinav.electronic.Helper.Helper;
import com.abhinav.electronic.Repositories.ProductRepo;
import com.abhinav.electronic.Service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public ProductDto createProduct(ProductDto productDto) {
		
		Product product = mapper.map(productDto, Product.class);
		//product id
	     String productId  = UUID.randomUUID().toString();
		 product.setProductId(productId);
		
		 //added
		 product.setAddedDate(new Date());
		Product savedProduct = this.productRepo.save(product); 
		
		return mapper.map(savedProduct, ProductDto.class);
	}

	@Override
	public ProductDto updateProduct(ProductDto productDto, String productId) {

		Product product = this.productRepo.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product","productId", productId));
		product.setTitle(productDto.getTitle());
		product.setDescription(productDto.getDescription());;
		product.setDiscountedPrice(productDto.getDiscountedPrice());
		product.setPrice(productDto.getPrice());
		product.setQuantity(productDto.getQuantity());
		product.setLive(productDto.isLive());
		product.setStock(productDto.isStock());
		
		Product updatedProduct = productRepo.save(product);
		return mapper.map(updatedProduct,ProductDto.class);
	}

	@Override
	public void deleteProduct(String productId) {
		
		Product product = this.productRepo.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product","productId", productId));
        productRepo.delete(product);
	}

	@Override
	public ProductDto getProductById(String productId) {

		Product product = this.productRepo.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product","productId", productId));
		
		return mapper.map(product,ProductDto.class);
	}

	@Override
	public PagebleResponse<ProductDto> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

		Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).descending()) ;
		Pageable pageable  = PageRequest.of(pageNumber,pageSize);
		Page<Product> page = productRepo.findAll(pageable);

		return Helper.getPagebleResponse(page,ProductDto.class);
	}

	@Override
	public PagebleResponse<ProductDto> getAllLive(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {


		Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).descending()) ;
		Pageable pageable  = PageRequest.of(pageNumber,pageSize);
		Page<Product> page = productRepo.findByLiveTrue(pageable);

		return Helper.getPagebleResponse(page,ProductDto.class);

	}

	@Override
	public PagebleResponse<ProductDto> searchByTitle(String subTitle, Integer pageNumber, Integer pageSize,String sortBy, String sortDir) {

		Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).descending()) ;
		Pageable pageable  = PageRequest.of(pageNumber,pageSize);
		Page<Product> page = productRepo.findByTitleContaining(subTitle,pageable);

		return Helper.getPagebleResponse(page,ProductDto.class);
		
	}

}
