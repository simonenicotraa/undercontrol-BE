package com.epicode.undercontrol.teams;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDto {

	private String name;

	private String season;

	private EGender gender;
	
	private String society;
}
  