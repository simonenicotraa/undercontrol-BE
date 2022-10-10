package com.epicode.undercontrol.security.auth.roles;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleConfiguration {

	@Bean("admin")
	public Role admin() {
		return new Role(ERole.ROLE_ADMIN);
	}
	@Bean("user")
	public Role user() {
		return new Role(ERole.ROLE_USER);
	}
}
