package com.diptopaul.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diptopaul.blog.entities.User;

import jakarta.persistence.Entity;

public interface UserRepo extends JpaRepository<User, Integer>{

}
