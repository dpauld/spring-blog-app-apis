package com.diptopaul.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diptopaul.blog.entities.Category;
import com.diptopaul.blog.entities.Post;
import com.diptopaul.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	//Q:Who will implement these method and how?
	//Answer: SimpleJpaRepository will provide the implementation using the metadata of the  abstract method
	
	//get all posts by category, the caller method will pass a Category and it will be used to find the corresponding posts. In real life user selects a category that triggers other methods and those methods call this abstract method.
	List<Post> findByCategory(Category category);
	
	//get all post by User, the caller method will pass a user and it will be used to find the corresponding posts. In real life when user are logged in we use the login info such as user name to fetch their posts.
	List<Post> findByUser(User user);
}
