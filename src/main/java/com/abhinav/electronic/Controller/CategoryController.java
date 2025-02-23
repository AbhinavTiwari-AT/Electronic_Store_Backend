package com.abhinav.electronic.Controller;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.abhinav.electronic.Dto.ApiResponseMessage;
import com.abhinav.electronic.Dto.CategoryDto;
import com.abhinav.electronic.Dto.ImageResponse;
import com.abhinav.electronic.Dto.PagebleResponse;
import com.abhinav.electronic.Service.CategoryService;
import com.abhinav.electronic.Service.FileService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController 
{
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${category.profile.image.path}")
    private String imageUploadPath;
	
	private Logger logger = LoggerFactory.getLogger(CategoryController.class);

	
	//create
	@PostMapping
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		
		//call service to save object
		CategoryDto categoryDto1 =categoryService.createCategory(categoryDto);
		return new ResponseEntity<>(categoryDto1,HttpStatus.CREATED);
	}
	
	//update
    @PutMapping("/{categoryId}")
 	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable String categoryId)
	{
		CategoryDto updatedCategory = categoryService.updateCategory(categoryDto, categoryId);
		return new ResponseEntity<>(updatedCategory,HttpStatus.ACCEPTED);	
	}
	
	//delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId)
    {
    	categoryService.delete(categoryId);
    	ApiResponseMessage responseMessage  = ApiResponseMessage.builder().message("Category is deleted successfully !!").status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }
	
	//get all
	@GetMapping
    public ResponseEntity<PagebleResponse<CategoryDto>> getAll(
    		@RequestParam(value = "pageNumber",defaultValue = "0",required = false)int pageNumber,
    		@RequestParam(value = "pageSize",defaultValue = "5",required = false)int pageSize,
    		@RequestParam(value = "sortBy",defaultValue = "title",required = false)String sortBy,
    		@RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
    		){
		 
		PagebleResponse<CategoryDto> pagebleResponse = categoryService.getAll(pageNumber,pageSize,sortBy,sortDir);
    	
    	return new ResponseEntity<>(pagebleResponse,HttpStatus.OK);
    }

	//get Single category
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getSingle(@PathVariable String categoryId)
	{
		CategoryDto categoryDto = categoryService.get(categoryId);
		
		return ResponseEntity.ok(categoryDto);
		
	}
	
	
	// upload user image
	
		@PostMapping("/image/{categoryId}")
		public ResponseEntity<ImageResponse> uploadUserImage(@PathVariable String categoryId , @RequestParam("categoryImage") MultipartFile image) throws IOException
		{
		 	 String imageName  =  fileService.uploadImage(image,imageUploadPath);
		 	 
		 	 CategoryDto categoryDto = categoryService.get(categoryId);
		 	 
		 	 categoryDto.setCoverImage(imageName);
		 	 
		     CategoryDto categoryDto1 = categoryService.updateCategory(categoryDto, categoryId);
		 	 
			 ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).message("image is created sucessfully").success(true).status(HttpStatus.CREATED).build();

			 return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
			
		}
	
		//serve user image
		
		@GetMapping("/image/{categoryId}")
		public void serveCategoryImage(@PathVariable String categoryId , HttpServletResponse response) throws IOException
		{
			//
		    CategoryDto categoryDto  = categoryService.get(categoryId);
		    logger.info("User image name : {} ", categoryDto.getCoverImage());
			InputStream resource = fileService.getResource(imageUploadPath, categoryDto.getCoverImage());
			
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			
		    StreamUtils.copy(resource,response.getOutputStream());
			
			
		}

}
