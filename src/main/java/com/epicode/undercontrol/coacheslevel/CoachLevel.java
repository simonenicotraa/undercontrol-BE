package com.epicode.undercontrol.coacheslevel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoachLevel {
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
private Long id;
@Column(nullable = false)
private ECoachLevel level;
}
