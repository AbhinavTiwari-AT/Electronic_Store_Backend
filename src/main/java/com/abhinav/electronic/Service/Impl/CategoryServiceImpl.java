package com.abhinav.electronic.Service.Impl;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

import com.abhinav.electronic.Dto.CategoryDto;
import com.abhinav.electronic.Dto.PagebleResponse;
import com.abhinav.electronic.Entities.Category;
import com.abhinav.electronic.Entities.User;
import com.abhinav.electronic.Exception.ResourceNotFoundException;
import com.abhinav.electronic.Helper.Helper;
import com.abhinav.electronic.Repositories.CategoryRepo;
import com.abhinav.electronic.Service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Value("${category.profile.image.path}")
	private String imagePath;
	
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {

		//creating categoryId:randomly
		
		String categoryId  = UUID.randomUUID().toString();
		categoryDto.setCategoryId(categoryId);
		
		Category category = this.modelMapper.map(categoryDto, Category.class);
	    Category  saved   = this.categoryRepo.save(category);
		return modelMapper.map(saved, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {

	  	Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId));
	  	
	  	//update category details
		 category.setTitle(categoryDto.getTitle());
		 category.setDescription(categoryDto.getDescription());
		 category.setCoverImage(categoryDto.getCoverImage());
		 
		 Category updated = this.categoryRepo.save(category);
		 return modelMapper.map(updated, CategoryDto.class);
	}



	@Override
	public void delete(String categoryId) {
		
      Category category	= this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
      
      
		// DELETE USER PROFILE IMAGE
		// image/user/abc.png
	    String fullPath =	imagePath + category.getCoverImage();
	    	    
	    try {
	    	
		    Path path = Paths.get(fullPath);

			Files.delete(path);
		} 
	    
	    catch (NoSuchFileException ex) 
	    {
          logger.info("User image not found in folder");
			ex.printStackTrace();

		} catch (IOException e) {
              
			e.printStackTrace();
		}
	    
		//DELETE USER
		
		this.categoryRepo.delete(category);	
		          
	}

	@Override
	public PagebleResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy , String sortDir) {
		
		Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
	   
		Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page<Category> page = this.categoryRepo.findAll(pageable); 
       
        PagebleResponse<CategoryDto> pagebleResponse = Helper.getPagebleResponse(page,CategoryDto.class);
        
		return pagebleResponse ;
	}

	@Override
	public CategoryDto get(String categoryId) {

	    Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
	    
		return modelMapper.map(category,CategoryDto.class);
	}

}
