package com.diptopaul.blog.controllers;

import java.util.List;

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

import com.diptopaul.blog.payloads.ApiResponse;
import com.diptopaul.blog.payloads.PostDto;
import com.diptopaul.blog.payloads.PostResponse;
import com.diptopaul.blog.services.PostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class PostController {
	//get the PostService bean object to use its method
	PostService postService;
	
	@Autowired
	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	@PostMapping("/users/{userId}/categories/{categoryId}/posts")
	ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId){
		PostDto createdPostDto = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<>(createdPostDto, HttpStatus.CREATED);
	}
	//update
	@PutMapping("/posts/{postId}")
	ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,@PathVariable Integer postId){
		PostDto updatedPostDto = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPostDto,HttpStatus.OK);
	}
	//delete
	@DeleteMapping("/posts/{postId}")
	ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
		this.postService.deletePost(postId);
		return new ResponseEntity<>(new ApiResponse("Post Deleted Successfully",true),HttpStatus.OK);
	}
	
	//get single ById
	@GetMapping("/posts/{postId}")
	ResponseEntity<PostDto> getPostByid(@PathVariable Integer postId){
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
				@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber, 
				@RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
				@RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
				@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir){
			PostResponse postResponse = this.postService.getAllPost(pageNumber,pageSize,sortBy, sortDir);
			return new ResponseEntity<>(postResponse,HttpStatus.OK);
		}
		
}
