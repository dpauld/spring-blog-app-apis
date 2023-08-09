package com.diptopaul.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diptopaul.blog.exceptions.ResourceNotFoundException;
import com.diptopaul.blog.payloads.ApiResponse;
import com.diptopaul.blog.payloads.CommentDto;
import com.diptopaul.blog.payloads.UserDto;
import com.diptopaul.blog.services.CommentService;
import com.diptopaul.blog.services.UserService;

@RestController
@RequestMapping("/api")
public class CommentController {
	private CommentService commentService;
	private UserService userService;
	
	@Autowired
	public CommentController(CommentService commentService, UserService userService) {
		this.commentService = commentService;
		this.userService = userService;
	}
	
	//create Comment
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@PostMapping("/posts/{postId}/comments")
	ResponseEntity<?> createComment(@Validated @RequestBody CommentDto commentDto, @PathVariable Integer postId) throws ResourceNotFoundException{
		//get the user ID
		String currentUsername = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDto userDto = this.userService.getUserByEmail(currentUsername);
		Integer currentUserId = userDto.getId();
		//System.out.println("From Controller " + currentUsername);
		CommentDto cDto = this.commentService.createComment(commentDto, currentUserId, postId);
		//System.out.println("From Controller " +  cDto.getUser().getEmail());
		return new ResponseEntity<CommentDto>(cDto, HttpStatus.CREATED);
	}
	
	//delete comment
	@PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and @customPermissionEvaluator.hasPermission(#commentId, 'Comment', 'edit'))")
	@DeleteMapping("/comments/{commentId}")
	ResponseEntity<?> deleteComment(@PathVariable("commentId") Integer commentId){
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully.", true), HttpStatus.OK);
	}
	
	//x not implementing update for comment
	//x get all comments is not usefull in real life, you wont ever see an use case of getting all the comments of all posts
}
