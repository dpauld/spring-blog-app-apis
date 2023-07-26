package com.diptopaul.blog.payloads;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//a special Request class for authentication that takes the request body when authinfo like username and password is passed

@Getter
@Setter
@NoArgsConstructor
public class AuthRequest {
	private String username;
	private String password;
}
