package com.diptopaul.blog.services;

import com.diptopaul.blog.payloads.CommentDto;

public interface CommentService{
	CommentDto createComment(CommentDto commentDto, Integer posId);
	void deleteComment(Integer commentId);
}
