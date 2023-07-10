package com.diptopaul.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diptopaul.blog.entities.Category;
import com.diptopaul.blog.exceptions.ResourceNotFoundException;
import com.diptopaul.blog.payloads.CategoryDto;
import com.diptopaul.blog.repositories.CategoryRepo;
import com.diptopaul.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	private CategoryRepo categoryRepo;
	private ModelMapper modelMapper;

	@Autowired
	public CategoryServiceImpl(CategoryRepo categoryRepo, ModelMapper modelMapper) {
		this.categoryRepo = categoryRepo;
		this.modelMapper = modelMapper;
	}

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		// TODO Auto-generated method stub
		
		//convert Dto type instance to Entity type instance
		Category category = this.modelMapper.map(categoryDto, Category.class);
		
		//call the Repository method to create a category
		Category categorySaved = this.categoryRepo.save(category);
		
		//convert the Entity type instance to Dto type instance and then return the result which CategoryDto type
		return this.modelMapper.map(categorySaved, CategoryDto.class);
	}

	@Override
	public CategoryDto updatCategory(CategoryDto categoryDto, Integer categoryId) {
		// TODO Auto-generated method stub
		//find the Category that needs to update
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
		
		//update the Category fields using the new values of CategoryDto
		category.setTitle(categoryDto.getTitle());
		category.setDescription(categoryDto.getDescription());
		
		//call the CategoryRepo method save to commit the update
		Category updatedCategory = this.categoryRepo.save(category);
		
		//convert the Category Entity type instance to CategoryDto type instance and then return it
		return this.modelMapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		//find the Category that needs to be deleted
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
		
		//call the method of CategoryRepo to perform the deletion
		this.categoryRepo.delete(category);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		//get all the Category from DB using the method of CategoryRepo
		List<Category> categories = this.categoryRepo.findAll();
		
		categories.forEach(catDto->{
			System.out.println(catDto.getTitle());
			});
		
		//convert all the categories to categoryDtos
		List<CategoryDto> categoryDtos = categories.stream().map(category->this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		
		//return the categoryDtos
		return categoryDtos;
	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		//use method of CategoryRepo to find a single Category
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));		
		
		//convert the category to Dto and then return it
		return this.modelMapper.map(category,CategoryDto.class);
	}

}
