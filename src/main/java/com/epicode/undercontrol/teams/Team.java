package com.epicode.undercontrol.teams;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.epicode.undercontrol.athletes.Athlete;
import com.epicode.undercontrol.coaches.Coach;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
private Long id;
private String name;
private String season;
private EGender gender;

private String society;

@ManyToMany
Set<Athlete> athletes = new HashSet<>();
@ManyToMany
Set<Coach> coaches = new HashSet<>();

public void addAthlete(Athlete a){
	athletes.add(a);
}
public void addCoach(Coach c){
	coaches.add(c);
}
public void removeAthlete(Athlete a){
	athletes.remove(a);
}
public void removeCoach(Coach c){
	coaches.remove(c);
}
}
