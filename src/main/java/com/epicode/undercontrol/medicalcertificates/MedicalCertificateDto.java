package com.epicode.undercontrol.medicalcertificates;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalCertificateDto {
	private LocalDate productionDate;
}
