package com.diptopaul.blog.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//a special response class for jwt that has the token 
@Getter
@Setter
@NoArgsConstructor
public class JwtAuthResponse {
	String token;
}
