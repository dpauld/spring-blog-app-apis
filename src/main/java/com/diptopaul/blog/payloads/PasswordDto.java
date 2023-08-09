package com.diptopaul.blog.payloads;

import com.diptopaul.blog.validationgroup.BaseValidation;
import com.diptopaul.blog.validationgroup.PasswordResetValidation;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PasswordDto {
	@JsonProperty(value="password", access = Access.WRITE_ONLY)
//	@NotPreviousValue //implement this annotation
	@Size(min=5, message = "password must be 5 charachters long.", groups = {PasswordResetValidation.class, BaseValidation.class})
	@Size(max=15, message = "password can not exceed 15 charachter.", groups = {PasswordResetValidation.class, BaseValidation.class})
	@Pattern(regexp = ".*[0-9].*", message = "password must contain one digit", groups = {PasswordResetValidation.class, BaseValidation.class})
	@NotEmpty(groups = {PasswordResetValidation.class, BaseValidation.class})
	//order of the annotation matters, the lower the order, it will be checked first
	private String password;
}
