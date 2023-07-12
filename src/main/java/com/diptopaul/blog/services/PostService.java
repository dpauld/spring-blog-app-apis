package com.diptopaul.blog.services;

import java.util.List;

import com.diptopaul.blog.entities.Post;
import com.diptopaul.blog.payloads.PostDto;
import com.diptopaul.blog.payloads.PostResponse;

public interface PostService {
	//create
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	//update
	PostDto updatePost(PostDto postDto, Integer postId);
	//delete
	void deletePost(Integer postId);
	
	//get single
	PostDto getPostById(Integer postId);
	//get by category
	List<PostDto> getByCategory(Integer categoryId);
	//get by user
	List<PostDto> getByUser(Integer userId);
	
	//get all
	List<PostDto> getAllPost();
	/*
	 * get all method for Pagination, that returns List of PostDto
	 */
	//List<PostDto> getAllPost(Integer pageNumber, Integer pageSize);
	
	/*
	 * get all method for Pagination, that returns PostResponse object that will have pagination information
	 */
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	List<PostDto> getByTitleContaining(String query);
}
