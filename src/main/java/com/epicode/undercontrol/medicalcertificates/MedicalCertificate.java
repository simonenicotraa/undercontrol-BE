package com.epicode.undercontrol.medicalcertificates;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.epicode.undercontrol.athletes.Athlete;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class MedicalCertificate {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private LocalDate productionDate;
	private LocalDate expirationDate;
	private boolean validation;
	
	
	public void verifyValidation() {
	if(expirationDate.equals(LocalDate.now()) || expirationDate.isAfter(LocalDate.now())) {
		
		validation=true;
	}else {
		validation=false;
	}
	}

}

