package com.diptopaul.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diptopaul.blog.entities.Role;
import com.diptopaul.blog.entities.User;

public interface RoleRepo extends JpaRepository<Role, Integer>{
	//Integer findByName(String name);
	//use Optional so that you can access orElse method to handle the Exception
	Optional<Role> findByName(String name);
}
