package com.diptopaul.blog.payloads;

import com.diptopaul.blog.annotations.UniqueEmail;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@JsonIgnoreProperties(value = "password", allowSetters = true, allowGetters = false)
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	private Integer id;
	
	@Size(min=4, message = "name must be min of 4 charachters!! ")
	@NotEmpty(message = "name required")
	private String name;
	
	@UniqueEmail
	@Pattern(regexp="(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])", message = "email is not valid")
	@Email(message = "email is not valid")//just @email accepts empty string, so need regex
	@NotEmpty(message = "email required")
	//order of the annotation matters, the lower the order, it will be checked first
	private String email;
	
	@JsonProperty(value="password", access = Access.WRITE_ONLY)
	@Size(min=5, message = "password must be 5 charachters long.")
	@Size(max=15, message = "password can not exceed 15 charachter.")
	@Pattern(regexp = ".*[0-9].*", message = "password must contain one digit")
	@NotEmpty
	//order of the annotation matters, the lower the order, it will be checked first
	private String password;
	
	@NotEmpty
	private String about;
}
