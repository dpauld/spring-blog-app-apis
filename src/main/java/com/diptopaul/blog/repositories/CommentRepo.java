package com.diptopaul.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diptopaul.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
