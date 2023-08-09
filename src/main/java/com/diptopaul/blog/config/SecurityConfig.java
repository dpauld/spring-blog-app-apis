package com.diptopaul.blog.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.diptopaul.blog.security.CustomAccessDeniedHandler;
import com.diptopaul.blog.security.CustomUserDetailsService;
import com.diptopaul.blog.security.JwtAuthenticationEntryPoint;
import com.diptopaul.blog.security.JwtAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	private CustomUserDetailsService customUserDetailsService;
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Autowired
	private CustomAccessDeniedHandler customAccessDeniedHandler;
	
	@Autowired
	public SecurityConfig(CustomUserDetailsService customUserDetailsService, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.customUserDetailsService = customUserDetailsService;
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Bean
	 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http
	        .csrf(csrf->csrf.disable())
	        .authorizeHttpRequests((authz) -> authz
	        		.requestMatchers("/api/v1/auth/**").permitAll()
	        		.requestMatchers("/api/v1/password/**").permitAll()//permit all to reset their password
	        		.requestMatchers(HttpMethod.GET,"/api/posts/**").permitAll()//all get method of posts controller is open for all
	        		.requestMatchers(HttpMethod.POST,"/api/posts/**").authenticated()//allow only authenticated user to send POST req to post api's
	        		.requestMatchers("/api/comments/**").authenticated()//allow only authenticated user to access comment api
	        		.requestMatchers(HttpMethod.GET,"api/categories/**").permitAll()
	        		.requestMatchers(HttpMethod.POST,"api/categories/**").hasRole("ADMIN")
	        		.requestMatchers(HttpMethod.PUT,"api/categories/**").hasRole("ADMIN")
	        		//delete and put are handled in the controller level methods, for user specific acess
	        		.anyRequest()
	        		.authenticated())
	        .exceptionHandling((e)->e.authenticationEntryPoint(this.jwtAuthenticationEntryPoint).accessDeniedHandler(customAccessDeniedHandler))//The AccessDeniedHandler only applies to authenticated users. Write codes for sending response for unathenticated user inside the jwtAuthenticationEntryPoint.
	        .sessionManagement((sessionManagement)->sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
	        
	        //basic auth is not needed now
	        //http.httpBasic(Customizer.withDefaults());
	        
	        http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	        
	        http.authenticationProvider(daoAuthenticationProvider());
	        return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}
	
	//this will be used for login api creation
	@Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        return new ProviderManager(daoAuthenticationProvider());
    }
	
}
