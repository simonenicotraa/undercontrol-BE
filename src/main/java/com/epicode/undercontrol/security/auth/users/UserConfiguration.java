package com.epicode.undercontrol.security.auth.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.epicode.undercontrol.security.auth.roles.Role;

@Configuration
public class UserConfiguration {
	@Autowired
	@Qualifier("admin")
	Role admin;

	@Bean("user1")
	public UserDto user1() {
		UserDto user = new UserDto("Gino", "giova","g.gino@gmail.com","Gino", "123456","team");
		
		return user;
	}


}
