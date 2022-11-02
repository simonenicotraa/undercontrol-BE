package com.epicode.undercontrol.athletes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.epicode.undercontrol.medicalcertificates.MedicalCertificate;
import com.epicode.undercontrol.payments.Payment;

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
	
	private String society;
	
	@OneToMany
	private List<MedicalCertificate> listCertificates= new ArrayList<MedicalCertificate>();
	
	@OneToMany
	private List<Payment> listPayments= new ArrayList<Payment>();
	
	
	public void addCertificate(MedicalCertificate m) {
		listCertificates.add(m);
	}
	public void removeCertificate(MedicalCertificate m) {
		listCertificates.remove(m);
	}
	public void addPayment(Payment p) {
		listPayments.add(p);
	}
	public void removePayment(Payment p) {
		listPayments.remove(p);
	}

} 
