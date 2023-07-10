package com.diptopaul.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diptopaul.blog.entities.Category;
import com.diptopaul.blog.entities.Post;
import com.diptopaul.blog.entities.User;
import com.diptopaul.blog.exceptions.ResourceNotFoundException;
import com.diptopaul.blog.payloads.PostDto;
import com.diptopaul.blog.repositories.CategoryRepo;
import com.diptopaul.blog.repositories.PostRepo;
import com.diptopaul.blog.repositories.UserRepo;
import com.diptopaul.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	//get the PostRepo to use its method
	PostRepo postRepo;
	//get the ModelMapper object to use its methods
	ModelMapper modelMapper;
	//get the UserRepo to use its method, because we might need those methods to fetch user info to attach them to their corresponding posts
	UserRepo userRepo;
	//for similar reason we need categoryRepo
	CategoryRepo categoryRepo;
	
	
	@Autowired
	public PostServiceImpl(PostRepo postRepo, UserRepo userRepo, CategoryRepo categoryRepo, ModelMapper modelMapper) {//spring beans works with Private constructor too, because it uses reflection which unable it to access anything regardless of access type
		this.postRepo = postRepo;
		this.userRepo = userRepo;
		this.categoryRepo = categoryRepo;
		this.modelMapper = modelMapper;
		// TODO Auto-generated constructor stub
	}
	/*
	 * 1. Implement 
	 */
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		//Get the User using the userId
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
		//Get the Category using the categoryId
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
				
		//convert postDto to Post
		Post post = this.modelMapper.map(postDto,Post.class);
		
		//add additional property that is not provided by user to the Post
		post.setImageName("Default.png");
		post.setAddedDate(new Date());
		post.setCategory(category);
		post.setUser(user);
		
		//now use you can use this post as the argument of PostRepo's save method, It can only take Post, cause we passed Post as the Type in the JpaRepository Generic diamond parameter 
		Post savedPost = this.postRepo.save(post);
		
		//convert the savedPost to DTO type and return it
		return this.modelMapper.map(savedPost, PostDto.class);
	}

	//task: got to add update facility of category
	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		//get the post by postId
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Id", postId));
		
		//Now use this post to update its property using the properties of postDto
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		//post.setCategory(postDto.getCategoryDto())  ;
		
		//now use you can use this post as the argument of PostRepo's save method, It can only take Post, cause we passed Post as the Type in the JpaRepository Generic diamond parameter 
		Post updatedPost = this.postRepo.save(post);
		
		//convert the savedPost to DTO type and return it
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		//could have used deleteById, then we had to deal with exception of resourceNoTFound differently
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Id", postId));
		this.postRepo.delete(post);
	}

	@Override
	public List<PostDto> getAllPost() {
		List<Post> posts = this.postRepo.findAll();
		
		//convert the posts to PostDtos
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
				
		return postDtos;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Id", postId));
		PostDto postDto = this.modelMapper.map(post,PostDto.class);
		return postDto;
	}

	@Override
	public List<PostDto> getByCategory(Integer categoryId) {
		//Use the categoryId to pass it to the method of categoryRepo to find the Category 
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
		
		//Now we can use this Category to find the list of post using the method of PostRepo which accepts only Category class object
		List<Post> posts = this.postRepo.findByCategory(category);
		
		//convert the posts to PostDtos
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		//return the PostDtos
		return postDtos;
	}

	@Override
	public List<PostDto> getByUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
		
		List<Post> posts = this.postRepo.findByUser(user);
		
		//convert the posts to PostDtos
		List<PostDto> postDtos = posts.stream().map((post)->modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	

}
