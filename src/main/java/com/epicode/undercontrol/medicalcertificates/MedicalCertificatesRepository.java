package com.epicode.undercontrol.medicalcertificates;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalCertificatesRepository extends JpaRepository<MedicalCertificate, Long> {

}
