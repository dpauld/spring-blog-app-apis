package com.diptopaul.blog.payloads;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
	
	private Integer id;
	
	@Size(min=4, max=100, message = "title must be min of 4 charachter and at most 100 charachter!!")
	@NotBlank
	private String title;
	
	@Size(max=250)
	@NotBlank
	private String description;
}
