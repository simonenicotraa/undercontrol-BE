package com.epicode.undercontrol;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.epicode.undercontrol.security.auth.roles.Role;
import com.epicode.undercontrol.security.auth.roles.RoleRepository;
import com.epicode.undercontrol.security.auth.users.User;
import com.epicode.undercontrol.security.auth.users.UserDto;
import com.epicode.undercontrol.security.auth.users.UserService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UnderControlRunner implements ApplicationRunner {
	RoleRepository roleRepository;
	UserService userService;
	@Qualifier("admin")	Role admin;
	@Qualifier("user")	Role user;
	@Qualifier("developer")	Role developer;
	@Qualifier("user1")	UserDto user1;
	@Qualifier("adminSupremo")	UserDto adminSupremo;
	@Override
	public void run(ApplicationArguments args) throws Exception {

//		roleRepository.save(admin);
//		roleRepository.save(user);
//		roleRepository.save(developer);
//
//		userService.insertAdmin(user1);
//		userService.insertAdminSupremo(adminSupremo);
	}

}
