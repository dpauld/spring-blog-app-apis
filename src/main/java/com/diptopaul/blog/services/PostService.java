package com.diptopaul.blog.services;

import java.util.List;

import com.diptopaul.blog.entities.Category;
import com.diptopaul.blog.entities.Post;
import com.diptopaul.blog.entities.User;
import com.diptopaul.blog.payloads.PostDto;

public interface PostService {
	//create
	 PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	//update
	 PostDto updatePost(PostDto postDto, Integer postId);
	//delete
	 void deletePost(Integer postId);
	//get all
	 List<PostDto> getAllPost();
	//get single
	 PostDto getPostById(Integer postId);
	//get by category
	 List<PostDto> getByCategory(Integer categoryId);
	//get by user
	 List<PostDto> getByUser(Integer userId);
}
