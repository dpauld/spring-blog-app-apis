package com.diptopaul.blog.payloads;

import com.diptopaul.blog.entities.Post;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class CommentDto {
	private Integer id;
	
	@NotBlank
	private String content;
	
	//private PostDto postDto;// this might cause data bind error, cause we needed to map it nested way from entity comment to Dto
	
	// dont put NotNull or this type of validation here, cause front-end might not provide the userDto, we will inject it in business logic layer
	private UserDto user;
}
