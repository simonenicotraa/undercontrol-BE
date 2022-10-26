package com.epicode.undercontrol.security.auth.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentialDto {
	private String username;
	private String password;
}
