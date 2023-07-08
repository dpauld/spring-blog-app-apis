package com.diptopaul.blog.services;

import java.util.List;

import com.diptopaul.blog.payloads.CategoryDto;

public interface CategoryService {
	//create
	CategoryDto createCategory(CategoryDto categoryDto);
	
	//update
	CategoryDto updatCategory(CategoryDto categoryDto, Integer categoryId);
	
	//delete
	void deleteCategory(Integer categoryId);
	
	//getAll
	List<CategoryDto> getAllCategory();
	
	//get single
	CategoryDto getCategoryById(Integer categoryId);
	
}
