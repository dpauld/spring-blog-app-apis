package com.diptopaul.blog.entities;

import java.util.HashSet;
import java.util.Set;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "role")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(unique = true, nullable = false)
	private String name;
	
	//this is optional, if we want to have unidirectional ManyToMany mapping where owning side is the User. 
	//Also unidirectional mapping is suitable when only want to have a Collection of one Entity, in this case we are having a Set of Role in the User side 

	//bidirectional ManyToMmany mapping
	//@ManyToMany(mappedBy = "roles")
	//private Set<User> users = new HashSet<>();

	//other options to achieve bidirectional ManyToMmany mapping, note: If a different table name is used here @JoinTable(name = "user_role",...), Two table will be created, so be careful.
//	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name="role_id", referencedColumnName="id"), inverseJoinColumns = @JoinColumn(name="user_id", referencedColumnName = "id"))
//	private Set<User> users = new HashSet<>();
}
