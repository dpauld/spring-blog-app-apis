package com.diptopaul.blog.security;

import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.diptopaul.blog.entities.User;
import com.diptopaul.blog.exceptions.ResourceNotFoundException;
import com.diptopaul.blog.repositories.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	private UserRepo userRepo;
	
	@Autowired
	public CustomUserDetailsService(UserRepo userRepo) {
		this.userRepo = userRepo;
	}
	/*
	 * Implementing loadUserByUsername this we can specify our own custom userName.
	 */
	
	//Implementing loadUserByUsername method by Implementing UserDetails by User class [Not a Good practice, User and UserDetails got coupled ]
//	@Deprecated
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		//load user from db by userName, for this project email is our userName.
//		System.out.println(username);
//		User user = this.userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User","Email",username));
//		return user;
//	}
	
	// Or Implementing with Implementing UserDetails a CustomUserDetails class, avoiding implementing by Entity User [Better, no coupling, also ensures more control]
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		//load user from db by userName, for this project email is our userName.
//		User user = this.userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User","Email",username));
//		return new CustomUserDetails(user);
//	}
		
		// or Implementing this method without Implementing UserDetails by User or a CustomUserDetails class [Bettter, no coupling, does not provide much control]
		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			//load user from db by userName, for this project email is our userName.
			//System.out.println(username);
			User user = this.userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User","Email",username));
			return new org.springframework.security.core.userdetails.User(
		            user.getUsername(),
		            user.getPassword(),
		            user.getRoles().stream().map((role)->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
		        );
		}

}
