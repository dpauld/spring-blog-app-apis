package com.diptopaul.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diptopaul.blog.entities.Comment;
import com.diptopaul.blog.entities.Post;
import com.diptopaul.blog.exceptions.ResourceNotFoundException;
import com.diptopaul.blog.payloads.CommentDto;
import com.diptopaul.blog.repositories.CommentRepo;
import com.diptopaul.blog.repositories.PostRepo;
import com.diptopaul.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	private CommentRepo commentRepo;
	private ModelMapper modelMapper;
	private PostRepo postRepo;
	
	@Autowired
	public CommentServiceImpl(CommentRepo commentRepo, PostRepo postRepo, ModelMapper modelMapper) {
		this.commentRepo = commentRepo;
		this.modelMapper = modelMapper;
		this.postRepo = postRepo;
	}
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer posId) {
		Post post = this.postRepo.findById(posId).orElseThrow(()->new ResourceNotFoundException("Post","Id",posId));
		
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

}
