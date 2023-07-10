package com.diptopaul.blog.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostResponse {
	//paged posts
	private List<PostDto> content;
	//
	private Integer pageNumber;
	private Integer pageSize;
	/**
	 * Returns total number of elements present in the current slice or chunk.
	 */
	private Integer totalElements;
	private Integer totalPages;
	private boolean lastPage;
	
}
