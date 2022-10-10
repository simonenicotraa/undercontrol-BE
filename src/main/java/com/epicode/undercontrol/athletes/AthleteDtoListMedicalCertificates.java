package com.epicode.undercontrol.athletes;

import java.util.HashSet;
import java.util.Set;

import com.epicode.undercontrol.medicalcertificates.MedicalCertificate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AthleteDtoListMedicalCertificates {
	private Set<MedicalCertificate> listCertificates= new HashSet<>() ;

}
