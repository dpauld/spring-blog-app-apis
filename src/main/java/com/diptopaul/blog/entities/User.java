package com.diptopaul.blog.entities;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Table(name="user")
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "name", length = 100, nullable = false)
	private String name;
	
	private String email;
	
	private String password;
	
	private String about;
	
	//adding posts field inside user
	//an user can have many posts, so OneToMany relationship
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Post> posts;//notice this attribute is not added in the database table of User, cause it's actually mapped by an attribute named user which is part of Post entity class, Hibernate and JPA will use this attribute name and their relationship to make a new field named `user_id` to represent the foreign key 
}
/*
 
 */
