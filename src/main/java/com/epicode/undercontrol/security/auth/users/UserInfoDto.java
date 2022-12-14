package com.epicode.undercontrol.security.auth.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
	private String name;
	private String surname;
	private String email;
}
