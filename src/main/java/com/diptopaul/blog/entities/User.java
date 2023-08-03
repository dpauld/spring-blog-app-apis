package com.diptopaul.blog.entities;


import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
@Setter
@Table(name="user")
@Entity
public class User{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "name", length = 100, nullable = false)
	private String name;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	private String about;
	
	//adding posts field inside user
	//an user can have many posts, so OneToMany relationship
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Post> posts;//notice this attribute is not added in the database table of User, cause it's actually mapped by an attribute named user which is part of Post entity class, Hibernate and JPA will use this attribute name and their relationship to make a new field named `user_id` to represent the foreign key 

	//role relationship, EAGER loading - associated roles will be fetched immediately when the user is fetched
	//referencedColumnName, if not specified JPA will consider the primary key of the corresponding table as the default
	@ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.REMOVE, CascadeType.MERGE}, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name="user_id", referencedColumnName="id"), inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName = "id"))
	private Set<Role> roles = new HashSet<>();

	public String getUsername() {
		return this.email;
	}
}
/*
 
 */
