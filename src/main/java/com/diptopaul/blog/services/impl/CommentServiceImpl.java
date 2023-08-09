package com.diptopaul.blog.services.impl;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diptopaul.blog.entities.Comment;
import com.diptopaul.blog.entities.Post;
import com.diptopaul.blog.entities.User;
import com.diptopaul.blog.exceptions.ResourceNotFoundException;
import com.diptopaul.blog.payloads.CommentDto;
import com.diptopaul.blog.payloads.UserDto;
import com.diptopaul.blog.repositories.CommentRepo;
import com.diptopaul.blog.repositories.PostRepo;
import com.diptopaul.blog.repositories.UserRepo;
import com.diptopaul.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	private CommentRepo commentRepo;
	private ModelMapper modelMapper;
	private PostRepo postRepo;
	private UserRepo userRepo;
	
	@Autowired
	public CommentServiceImpl(CommentRepo commentRepo, PostRepo postRepo, ModelMapper modelMapper, UserRepo userRepo) {
		this.commentRepo = commentRepo;
		this.modelMapper = modelMapper;
		this.postRepo = postRepo;
		this.userRepo = userRepo;
	}
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
		
		//convert Dto to Entity
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		
		//set the post of the comment, since user frontend client wont provide the post inside CommentDto
		comment.setPost(post);
		
		//save using repo method
		Comment savedComment = this.commentRepo.save(comment);
		
		//convert returned Entity to Dto and then return it
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","Id",commentId));
		this.commentRepo.delete(comment);
	}
	
	@Override
	public boolean isOwnerOfComment(Integer commentId, String username) {
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","Id",commentId));
		User user = comment.getUser();
		return username.equals(user.getUsername());
	}

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer userId, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
		
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
		//System.out.println(user.getEmail());
		//convert Dto to Entity
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		
		//set the post of the comment, since user frontend client wont provide the post inside CommentDto
		comment.setPost(post);
		//set the user
		comment.setUser(user);
		
		//save using repo method
		Comment savedComment = this.commentRepo.save(comment);
		//System.out.println(savedComment.getUser().getEmail());
		
		//convert returned Entity to Dto and then return it
		CommentDto saveCommentDto = this.modelMapper.map(savedComment, CommentDto.class);
		//modelMapper for Comment to CommentDto acting weird handling nested User, when two User are there, entity Comment has a element User, also another element Post has a User
		
		//commentDto is acting weird, mapping the owner of post as the owner of comment,
		//so handle this weirdness of user seperately, just update the commentDto user by the Authenticated user we fetched in the Dto formate
		saveCommentDto.setUser(modelMapper.map(savedComment.getUser(), UserDto.class));
		
		//System.out.println(saveCommentDto.getUser().getEmail());
		return saveCommentDto;
	}
	
	
	// Create a custom converter to map the User object to UserDto
	private CommentDto CommentToDto(Comment comment, CommentDto commentDto) {
		commentDto.setContent(comment.getContent());
		comment.setId(comment.getId());
		//comment.setPost(comment.getPost());
		comment.setUser(comment.getUser());
		return commentDto;
	}
	
	private Comment dtoToComment(CommentDto commentDto, Comment comment) {
		comment.setContent(commentDto.getContent());
		comment.setId(commentDto.getId());
		//comment.setPost(commentDto.getPost());
		comment.setUser(modelMapper.map(commentDto.getUser(), User.class));
		return comment;
	}
}
