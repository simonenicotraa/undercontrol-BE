package com.epicode.undercontrol.teams;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.epicode.undercontrol.coaches.Coach;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDtoListCoaches {
	
	Set<Coach> coach = new HashSet<>();
}
