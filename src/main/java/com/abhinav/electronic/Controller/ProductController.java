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
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.abhinav.electronic.Dto.ImageResponse;
import com.abhinav.electronic.Dto.PagebleResponse;
import com.abhinav.electronic.Dto.ProductDto;
import com.abhinav.electronic.Service.FileService;
import com.abhinav.electronic.Service.ProductService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/products")
@Tag(name = "ProductController", description = "Controller for managing products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${product.image.path}")
	private String imagePath;
	
	private Logger logger = LoggerFactory.getLogger(ProductController.class);

	
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
	
	//upload product image
	@PostMapping("/image/{productId}")
	public ResponseEntity<ImageResponse> uploadProductImage(@PathVariable String productId,@RequestParam("productImage")MultipartFile image ) throws IOException
	{
	    String fileName	= fileService.uploadImage(image,imagePath);
		
		ProductDto productDto = productService.getProductById(productId);
		
		productDto.setProductImageName(fileName);
		
		productService.updateProduct(productDto, productId);
		
		ImageResponse imageResponse = ImageResponse.builder().imageName(fileName).message("Image added successfully").success(true).status(HttpStatus.CREATED).build();
		
		return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
	}
	
	
	//serve image
	
	@GetMapping("/image/{productId}")
	public void serveProductImage(@PathVariable String productId , HttpServletResponse response) throws IOException
	{
		//
		ProductDto productDto = this.productService.getProductById(productId);
		
	    logger.info("User image name : {} ",productDto.getProductImageName());
		InputStream resource = fileService.getResource(imagePath, productDto.getProductImageName());
		
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		
	    StreamUtils.copy(resource,response.getOutputStream());
		
		
	}
	

}
