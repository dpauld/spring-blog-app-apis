package com.diptopaul.blog.payloads;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.diptopaul.blog.entities.Comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	private Integer id;
	
	@Size(min=1,max=150,message = "title must be longer that 1 charachter")
	@Size(max=150,message = "title cannot exceed 150 characters.")
	@NotBlank(message = "title required")
	private String title;
	
	@Size(max=10000,message = "content size is too long")
	@NotBlank(message = "content required")
	private String content;
	
	private String imageName;
	private Date addedDate;
	private CategoryDto category;
	private UserDto user;
	
	private List<CommentDto> comments=new ArrayList<>();//=new ArrayList<>();//instead of null, creating arrayList will create an Empty array as []
	//use Set if you don't want to have sorting or specific order
}
