package com.epicode.undercontrol.medicalcertificates;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.epicode.undercontrol.athletes.Athlete;
import com.epicode.undercontrol.athletes.AthleteRepository;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class MedicalCertificateService {
	private AthleteRepository athlRepo;
	private MedicalCertificatesRepository repo;
	
	// metodo per restituire tutti gli oggett
		public List<MedicalCertificate> findAll() {
			List<MedicalCertificate> findAll = (List<MedicalCertificate>) repo.findAll();
			for (MedicalCertificate ele : findAll) {
				ele.verifyValidation();
			}
			repo.flush();
			repo.saveAll(findAll);
			log.info("Found {} MedicalCertificate", findAll.size());
			return findAll;
		}


		// Ottenere oggetti tramite Id
		public Optional<MedicalCertificate> getById(Long id) {
			log.info("Finding object: id {}", id);
			Optional<MedicalCertificate> resultOptional = repo.findById(id);
			if (resultOptional.isPresent()) {
				log.info("Found MedicalCertificate: id {}", id);
			} else {
				log.info("Not found MedicalCertificate: id {}", id);
			}
			return resultOptional;
		}

		// Eliminare tramite oggetti Id
		public void deleteById(Long id,Long idAthl) {
			if (!repo.existsById(id)) {
				throw new EntityNotFoundException("MedicalCertificate not found");
			}
			Athlete a = athlRepo.findById(idAthl).get();
			MedicalCertificate m = repo.getById(id);
			a.removeCertificate(m);
			repo.deleteById(id);
			log.info("MedicalCertificate deleted");
		}

		// Metodo per Salvataggio oggetti
		public MedicalCertificate insert(MedicalCertificateDto objectToInsert, Long id) throws Exception {
			if(objectToInsert.getProductionDate()==null) {
				throw new Exception("Insert Medical Production Date");
			}
			log.info("Inserting MedicalCertificate: {}", objectToInsert);
			Athlete a = athlRepo.findById(id).get();
			MedicalCertificate medicalCertificate = new MedicalCertificate();
			// copio le proprietà del dto nell'entity principale
			BeanUtils.copyProperties(objectToInsert, medicalCertificate);
			// setto la data di scadenza del certificato medico
			medicalCertificate.setExpirationDate(medicalCertificate.getProductionDate().plusDays(365));
			medicalCertificate.verifyValidation();
			//aggiungo il certificato alla lista di certificati
			a.addCertificate(medicalCertificate);
			
			repo.save(medicalCertificate);
			
			
			log.info("Inserted MedicalCertificate: {}", medicalCertificate);
			return medicalCertificate;
		}
		
		//Metodo per modificare Dati oggetti
		public MedicalCertificate update(Long id, MedicalCertificateDto dto) throws Exception {
			//verifico se esiste l'utente con id che passo
			if (!repo.existsById(id)) {
				throw new EntityNotFoundException("MedicalCertificate Not Found");
			}else if(dto.getProductionDate()==null) {
				throw new Exception("Insert Medical Production Date");
			}
			//ottengo l'oggetto che voglio tramite id
			MedicalCertificate medicalCertificate = getById(id).get();
			//copio le proprietà dto nell'entity principale
			BeanUtils.copyProperties(dto, medicalCertificate);
			// setto la data di scadenza del certificato medico
			medicalCertificate.setExpirationDate(medicalCertificate.getProductionDate().plusDays(365));
			//salvo nel db
			return repo.save(medicalCertificate);
		}
}
