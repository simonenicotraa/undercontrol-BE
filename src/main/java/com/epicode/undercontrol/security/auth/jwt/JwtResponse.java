package com.epicode.undercontrol.security.auth.jwt;

import java.util.List;

import lombok.Data;

@Data
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private String society;
	private List<String> roles;

	public JwtResponse(String accessToken, Long id, String username,String society, List<String> roles) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.society=society;
		this.roles = roles;
	}
}
