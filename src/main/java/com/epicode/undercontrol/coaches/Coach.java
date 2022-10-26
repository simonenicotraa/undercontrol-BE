package com.epicode.undercontrol.coaches;

import java.time.LocalDate;
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
public class Coach {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String surname;
	@Column(nullable = false)
	private LocalDate dateOfBirth;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private String nTel;
	@Column(length = 16,nullable = false)
	private String fiscalCode;
	private String address;
	@Column(length = 5,nullable = false)
	private String cap;
	
	private String society;
}
