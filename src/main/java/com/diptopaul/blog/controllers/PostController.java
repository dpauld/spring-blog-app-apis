package com.diptopaul.blog.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.cloudinary.api.exceptions.ApiException;
import com.cloudinary.utils.ObjectUtils;
import com.diptopaul.blog.annotations.MultipartFileSize;
import com.diptopaul.blog.config.AppConstants;
import com.diptopaul.blog.exceptions.ResourceNotFoundException;
import com.diptopaul.blog.payloads.ApiResponse;
import com.diptopaul.blog.payloads.PostDto;
import com.diptopaul.blog.payloads.PostResponse;
import com.diptopaul.blog.services.FileService;
import com.diptopaul.blog.services.PostService;
import com.diptopaul.blog.payloads.FileResponse;

import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class PostController {
	//get the PostService bean object to use its method
	PostService postService;
	FileService fileService;
	
	@Value("${project.upload-diretory-path}")
	private String UPLOAD_DIR_PATH;
	
	@Value("${cloudinary.upload-folder-name}")
	private String UPLOAD_FOLDER_NAME;
	
	@Autowired
	public PostController(PostService postService, FileService fileService) {
		this.postService = postService;
		this.fileService = fileService;
	}
	
	@PostMapping("/users/{userId}/categories/{categoryId}/posts")
	ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId){
		PostDto createdPostDto = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<>(createdPostDto, HttpStatus.CREATED);
	}
	//update
//	@PutMapping("/posts/{postId}")
//	ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,@PathVariable Integer postId){
//		PostDto updatedPostDto = this.postService.updatePost(postDto, postId);
//		return new ResponseEntity<PostDto>(updatedPostDto,HttpStatus.OK);
//	}
	@PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and @customPermissionEvaluator.hasPermission(#postId, 'Post', 'edit'))")
	@PutMapping("/posts/{postId}")
	ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,@PathVariable Integer postId){
		PostDto updatedPostDto = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPostDto,HttpStatus.OK);
	}
	
	//delete
	@PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and @customPermissionEvaluator.hasPermission(#postId, 'Post', 'edit'))")
	@DeleteMapping("/posts/{postId}")
	ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
		this.postService.deletePost(postId);
		return new ResponseEntity<>(new ApiResponse("Post Deleted Successfully",true),HttpStatus.OK);
	}
	
	//get single ById
	@GetMapping("/posts/{postId}")
	ResponseEntity<PostDto> getPostByid(@PathVariable Integer postId){
		System.out.println("posts single");
		PostDto postDto = this.postService.getPostById(postId);
		return new ResponseEntity<>(postDto,HttpStatus.OK);
	}
	
	//getByUser
	@GetMapping("/users/{userId}/posts")
	ResponseEntity<List<PostDto>> getByUser(@PathVariable Integer userId){
		//get the posts as Dtos format
		List<PostDto> postDtos = this.postService.getByUser(userId);
		
		//return them
		return new ResponseEntity<>(postDtos,HttpStatus.OK);
	}
	//getByCategory
	@GetMapping("/categories/{categoryId}/posts")
	ResponseEntity<List<PostDto>> getByCategory(@PathVariable Integer categoryId){
		//get the posts as Dtos format
		List<PostDto> postDtos = this.postService.getByCategory(categoryId);
		
		//return them
		return new ResponseEntity<>(postDtos,HttpStatus.OK);
	}
		
		//get all without pagination and sorting
		
//		@GetMapping("/posts")
//		@Deprecated
//		ResponseEntity<List<PostDto>> getAllPost(){
//			List<PostDto> postDtos = this.postService.getAllPost();
//			return new ResponseEntity<>(postDtos,HttpStatus.OK);
//		}
	
		//get all with pagination and sorting
//		@GetMapping("/posts")
//		@Deprecated
//		ResponseEntity<List<PostDto>> getAllPost(
//				@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber, 
//				@RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize){
//			List<PostDto> postDtos = this.postService.getAllPost(pageNumber,pageSize);
//			return new ResponseEntity<>(postDtos,HttpStatus.OK);
//		}
		
		/*
		* get all method for Pagination, that returns PostResponse object( which will have pagination information )
		*/
		@GetMapping("/posts")
		ResponseEntity<PostResponse> getAllPost(
				@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber, 
				@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
				@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
				@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir){
			PostResponse postResponse = this.postService.getAllPost(pageNumber,pageSize,sortBy, sortDir);
			return new ResponseEntity<>(postResponse,HttpStatus.OK);
		}
		
		//search
		@GetMapping("/search")
		ResponseEntity<List<PostDto>> getByTitleContaining(@RequestParam(value = "query", required = false) String query){
			List<PostDto> postDtos = this.postService.getByTitleContaining(query);
			return new ResponseEntity<>(postDtos, HttpStatus.OK);
			
		}
		/*
		 * <h3>#########Uploading in File System#########</h3>
		 */
		//image controller methods
		//this post method is more of a update image for the post. I might add image addition feature when the post was being created(In Future).
//		@Deprecated
//		@PostMapping("/posts/image/upload/{postId}")
//		public ResponseEntity<PostDto> uploadFile(@Nonnull @RequestParam("image") MultipartFile file, @PathVariable("postId") Integer postId) throws IOException, ResourceNotFoundException, ApiException {
//			//first find the PostDto a representation of Post, if it exist then there is a point to upload the image, otherwise no reason
//			PostDto postDto = this.postService.getPostById(postId);
//			
//			System.out.println(UPLOAD_DIR_PATH);
//			String newFileName = (String) this.fileService.uploadFile(UPLOAD_DIR_PATH, file);
//			
//			//update the PostDto then also the Post using the added image
//			postDto.setImageName(newFileName);
//			PostDto updatePostDto = this.postService.updatePost(postDto, postId);
//			
//			//you can return the FileResponse as well as PostDto as well
//			
//		    //return ResponseEntity.ok(new FileResponse(newFileName, "Image is uploaded successfully!"));
//			return ResponseEntity.ok(updatePostDto);
//		}
		
		//sending the image mimeType Dynamic
		//this can be improved, usually user might want to get the image when they visit the post, so we can get the post id and then get the image name and serve the post. Here i am serving the post with a direct image name.
//		@GetMapping(value = "/posts/image/{imageName}")
//		public void getImage(@PathVariable("imageName") String imageName, HttpServletResponse httpServletResponse) throws IOException,FileNotFoundException {
//			InputStream inputStream  = this.fileService.getFile(UPLOAD_DIR_PATH, imageName);
//		
//			//dynamically get the mediaType
//			String mimeType = URLConnection.guessContentTypeFromStream(inputStream);
//			httpServletResponse.setContentType(mimeType);
//			httpServletResponse.addHeader("Content-Type", mimeType);
//			//source: https://stackoverflow.com/questions/51438/how-to-get-a-files-media-type-mime-type
//			
//			StreamUtils.copy(inputStream, httpServletResponse.getOutputStream());
//		}
//		
		/*
		 * <h3>#########Cloudinary CRUD in to a folder(upload to the project specific sub folder)#########</h3>
		 */
		@PostMapping("/posts/image/upload/{postId}")
		//@MultipartFileSize is not working here, maybe using in an entity might work
		ResponseEntity<?> uploadImage(@Nonnull @MultipartFileSize(max = AppConstants.MAX_FILE_SIZE, message = "File size should be less than 10MB") @RequestParam("image") MultipartFile imageFileReceived, @PathVariable Integer postId) throws IOException, ApiException{
			//first find the PostDto a representation of Post, if it exist then there is a point to upload the image, otherwise no reason
			PostDto postDto = this.postService.getPostById(postId);
			
			//upload the image
			String folderName = UPLOAD_FOLDER_NAME;
			Object url = fileService.uploadFile(folderName, imageFileReceived);
			if(url!=null) {
				postDto.setImageName(url.toString());
			}
			//now update the post by this postDto
			PostDto updatedPostDto = this.postService.updatePost(postDto, postId);
			
			return new ResponseEntity<PostDto>(updatedPostDto, HttpStatus.OK);
		}
		
		//delete using cloudinary destroy method, it's a admin api method. Gotta find out how to delete using client side api token
		//Have to store the signature when uploading the image, currently not implementing it
//		@DeleteMapping("/delete")
//		public ResponseEntity<?> deleteImage(@RequestParam("public_id") String publicId, @RequestParam("signature") String signature) throws IOException {
//			Map<String, Object> params = new HashMap<>();
//			params.put("signature", signature);
//			
//			Map response = cloudinary.uploader().destroy(publicId, params);
//			//try destroy_method
//			//https://stackoverflow.com/questions/59802777/delete-an-image-from-cloudinary-using-rest-full-api
//			//https://cloudinary.com/documentation/image_upload_api_reference#destroy_method
//			return new ResponseEntity<Map>(response, HttpStatus.OK);
//		}
		
}
