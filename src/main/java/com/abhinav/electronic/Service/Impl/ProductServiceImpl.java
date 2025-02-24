package com.abhinav.electronic.Service.Impl;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.abhinav.electronic.Dto.PagebleResponse;
import com.abhinav.electronic.Dto.ProductDto;
import com.abhinav.electronic.Entities.Category;
import com.abhinav.electronic.Entities.Product;
import com.abhinav.electronic.Exception.ResourceNotFoundException;
import com.abhinav.electronic.Helper.Helper;
import com.abhinav.electronic.Repositories.CategoryRepo;
import com.abhinav.electronic.Repositories.ProductRepo;
import com.abhinav.electronic.Service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Value("${product.image.path}")
	private String imagePath;
	
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


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
		product.setProductImageName(productDto.getProductImageName());
		
		Product updatedProduct = productRepo.save(product);
		return mapper.map(updatedProduct,ProductDto.class);
	}

	@Override
	public void deleteProduct(String productId) {
		
		Product product = this.productRepo.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product","productId", productId));
        		
		// DELETE Product PROFILE IMAGE
		// image/user/abc.png
	    String fullPath =	imagePath + product.getProductImageName();
	    	    
	    try {
	    	
		    Path path = Paths.get(fullPath);

			Files.delete(path);
		} 
	    
	    catch (NoSuchFileException ex) 
	    {
            logger.info("Product image not found in folder");
			ex.printStackTrace();

		} catch (IOException e) {
                
			e.printStackTrace();
		}
	    
		//DELETE USER
		
		this.productRepo.delete(product);	
		          
	}

	@Override
	public ProductDto getProductById(String productId) {

		Product product = this.productRepo.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product","productId", productId));
		
		return mapper.map(product,ProductDto.class);
	}

	@Override
	public PagebleResponse<ProductDto> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

		Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		
		Pageable pageable  = PageRequest.of(pageNumber,pageSize,sort);
		
		Page<Product> page = productRepo.findAll(pageable);

		return Helper.getPagebleResponse(page,ProductDto.class);
		
	}

	@Override
	public PagebleResponse<ProductDto> getAllLive(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {


		Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending()) ;
		Pageable pageable  = PageRequest.of(pageNumber,pageSize);
		Page<Product> page = productRepo.findByLiveTrue(pageable);

		return Helper.getPagebleResponse(page,ProductDto.class);

	}

	@Override
	public PagebleResponse<ProductDto> searchByTitle(String subTitle, Integer pageNumber, Integer pageSize,String sortBy, String sortDir) {

		Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending()) ;
		Pageable pageable  = PageRequest.of(pageNumber,pageSize);
		Page<Product> page = productRepo.findByTitleContaining(subTitle,pageable);

		return Helper.getPagebleResponse(page,ProductDto.class);
		
	}



	@Override
	public ProductDto createProductwithCategory(ProductDto productDto, String categoryId) {

		//fetch the category from db:
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","categoryId", categoryId));
		
		Product product = mapper.map(productDto, Product.class);
		//product id
	    String productId  = UUID.randomUUID().toString();
		product.setProductId(productId);
		
		//added
		product.setAddedDate(new Date());
		product.setCategory(category);
		Product savedProduct = this.productRepo.save(product); 
		
		return mapper.map(savedProduct, ProductDto.class);
	}
	
	
	@Override
	public ProductDto updateCategory(String productId, String categoryId) {

		//product fetch operation
		Product product = this.productRepo.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product", "productId", productId));
		
		// fetch category operation
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","categoryId", categoryId));
		
		product.setCategory(category);
		
		Product save = productRepo.save(product);
		
		return mapper.map(save, ProductDto.class);
	}

	
	@Override
	public PagebleResponse<ProductDto> getAllofCategory(String categoryId, int pageNumber, int pageSize, String sortBy,
			String sortDir)
	{
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","categoryId", categoryId));

		Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending()) ;

        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
        
		Page<Product> page = productRepo.findByCategory(category,pageable);

		return Helper.getPagebleResponse(page,ProductDto.class);

	}

	
}
