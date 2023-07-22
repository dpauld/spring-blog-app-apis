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
	//private Post post;// this might cause data bind error, cause we needed to map it nested way from entity comment to Dto
}
