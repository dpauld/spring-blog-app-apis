package com.diptopaul.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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
import com.diptopaul.blog.services.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {
	CommentService commentService;
	
	@Autowired
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	//create Comment
	@PostMapping("/posts/{postId}/comments")
	ResponseEntity<?> createComment(@Validated @RequestBody CommentDto commentDto, @PathVariable Integer postId) throws ResourceNotFoundException{
		CommentDto cDto = this.commentService.createComment(commentDto, postId);
		return new ResponseEntity<CommentDto>(cDto, HttpStatus.CREATED);
	}
	
	//delete comment
	@DeleteMapping("/comments/{commentId}")
	ResponseEntity<?> deleteComment(@PathVariable("commentId") Integer commentId){
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully.", true), HttpStatus.OK);
	}
	
	//x not implementing update for comment
	//x get all comments is not usefull in real life, you wont ever see an use case of getting all the comments of all posts
}
