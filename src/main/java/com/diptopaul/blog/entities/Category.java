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

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="category")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Integer id;
	
	@Column(name = "title", length = 100, nullable = false)
	private String title;
	
	@Column(name = "description")
	private String description;
	
	//we want to have each post must register with a category
	//thus one category can have many posts
	@OneToMany(mappedBy = "category",cascade = CascadeType.ALL, fetch = FetchType.LAZY)//telling that this field is mapped by a field named category
	//By CascadeType.ALL telling If we do anything with category say delete or change then do the same thing with their corresponding posts
	//FetchType.LAZY tells that fetch only the posts not its child such as comments
	private List<Post> posts;
}
/*
 
INSERT INTO blog_app_apis.category (`id`,`description`,`title`) VALUES (2,'This Category contains topics related to Programming Languages','Programming Languages');
INSERT INTO blog_app_apis.category (`id`,`description`,`title`) VALUES (3,'This Category contains topics related to General Knowledge','General Knowledge');
INSERT INTO blog_app_apis.category (`id`,`description`,`title`) VALUES (102,'All the Physics related topics are represted by this Category','Physics');
 
 */

