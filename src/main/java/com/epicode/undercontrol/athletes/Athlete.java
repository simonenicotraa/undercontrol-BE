package com.epicode.undercontrol.athletes;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.epicode.undercontrol.medicalcertificates.MedicalCertificate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Athlete {
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
	
	private String nTel;
	@Column(length = 16,nullable = false)
	private String fiscalCode;
	private String address;
	@Column(length = 5,nullable = false)
	private String cap;
	@OneToMany
	private Set<MedicalCertificate> listCertificates= new HashSet<>() ;
	
	
	public void addCertificate(MedicalCertificate m) {
		listCertificates.add(m);
	}
} 
