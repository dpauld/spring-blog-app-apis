package com.diptopaul.blog.security;

import java.io.Serializable;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.diptopaul.blog.entities.User;
import com.diptopaul.blog.payloads.UserDto;
import com.diptopaul.blog.services.CommentService;
import com.diptopaul.blog.services.PostService;
import com.diptopaul.blog.services.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component("customPermissionEvaluator")
public class CustomPermissionEvaluator implements PermissionEvaluator {
	private PostService postService;
	private ModelMapper modelMapper;
	private UserService userService;
	private CommentService commentService;
	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
		return false;
	}
	
	public boolean hasPermission(Serializable targetId, String targetType,
			Object permission) {
		System.out.println("Here");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication!=null) 
		{
			String currentUsername = (String) authentication.getPrincipal();
			System.out.println(currentUsername);
			if (targetId instanceof Long || targetId instanceof Integer){
				if ("Post".equals(targetType)) {
		            // Handle permission check for a Post object
					Integer postId = (Integer) targetId;
		            return postService.isOwnerOfPost(postId, currentUsername);
		        } else if ("User".equals(targetType)) {
		        	Integer userId = (Integer) targetId;
		        	return this.userService.isOwnerOfUser(userId, currentUsername);
		        }
				else if ("Video".equals(targetType)) {
		            // Handle permission check for a Video object
		        } else if ("Comment".equals(targetType)) {
		            // Handle permission check for a Comment object
		        	Integer commentId = (Integer) targetId;
		        	return this.commentService.isOwnerOfComment(commentId, currentUsername);
		        }
		    }
		}
		return false;
	}


}
