package com.diptopaul.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.diptopaul.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
	//Repository deals with Entity, not Dtos, so whenever you use methods of Repository make sure you pass instance of Entity not the Instance of Dtos, also
	// methods of repsitory returns instances of Entity type for example: user, category. Not userDto, not categoryDto. 
	//However for the rest of the other layers of packages such as service, controller we use Dtos to pass the data in between them, so whenever we use repo we have to convert the Dtos to Entity type
}
