package com.epicode.undercontrol.security.auth.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.epicode.undercontrol.security.auth.roles.Role;

@Configuration
public class UserConfiguration {
	@Autowired @Qualifier("admin")	Role admin;
	@Qualifier("developer")	Role developer;
	
	@Bean("adminSupremo")
	public UserDto adminDeveloper() {
		UserDto user = new UserDto("AdminSupremo", "Simone","simone.nicotra95@gmail.com","admin", "123456","UnderControl");
		return user;
	}
	
	@Bean("user1")
	public UserDto user1() {
		UserDto user = new UserDto("Gino", "giova","g.gino@gmail.com","Gino", "123456","team");
		return user;
	}
	


}
