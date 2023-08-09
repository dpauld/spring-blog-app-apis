package com.diptopaul.blog;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.diptopaul.blog.repositories.UserRepo;

import lombok.AllArgsConstructor;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}

	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UserRepo userRepo;
	@Override
	public void run(String... args) throws Exception {
//		System.out.println(emali_str.toLowerCase());
//		System.out.println(this.passwordEncoder.encode("abir15"));
//		System.out.println(userRepo.findByEmail("dipto@gmail.com").get().getRoles().stream().map((role)->new SimpleGrantedAuthority(role.getName())).toList());
//		
	}

	//Not used, added a config file istead
//	@Bean
//	public ModelMapper modelMapper() {
//	   return new ModelMapper();
//	}

}

