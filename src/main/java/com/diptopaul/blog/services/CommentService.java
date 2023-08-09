package com.diptopaul.blog.services;

import com.diptopaul.blog.payloads.CommentDto;

public interface CommentService{
	CommentDto createComment(CommentDto commentDto, Integer posId);
	void deleteComment(Integer commentId);
	boolean isOwnerOfComment(Integer commentId, String username);
	CommentDto createComment(CommentDto commentDto, Integer currentUserId, Integer postId);
}
