package com.epicode.undercontrol.athletes;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.epicode.undercontrol.medicalcertificates.MedicalCertificate;
import com.epicode.undercontrol.medicalcertificates.MedicalCertificateDto;
import com.epicode.undercontrol.medicalcertificates.MedicalCertificateService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class AthleteService {
	private AthleteRepository athleteRepo;
	private MedicalCertificateService medicalCertificate;

	// metodo per restituire tutti gli atleti
	public List<Athlete> findAll() {
		List<Athlete> findAll = (List<Athlete>) athleteRepo.findAll();
		log.info("Found {} Athletes", findAll.size());
		return findAll;
	}

	// Ottenere Utente tramite username - NO Case Sensitive
	public Optional<Athlete> getByFiscalCode(String name, boolean ignoreCase) {
		log.info("Finding object: name {} with ignoreCase {}", name, ignoreCase);
		Optional<Athlete> resultOptional = Optional.empty();
		if (ignoreCase) {
			resultOptional = athleteRepo.findByFiscalCodeIgnoreCase(name);
		} else {
			resultOptional = athleteRepo.findByFiscalCodeIgnoreCase(name);
		}
		if (resultOptional.isPresent()) {
			log.info("Found object: name {}", name);
		} else {
			log.info("Not found object: name {}", name);
		}
		return resultOptional;
	}

	// Ottenere Utente tramite Id
	public Optional<Athlete> getById(Long id) {
		log.info("Finding object: id {}", id);
		Optional<Athlete> resultOptional = athleteRepo.findById(id);
		if (resultOptional.isPresent()) {
			log.info("Found object: id {}", id);
		} else {
			log.info("Not found object: id {}", id);
		}
		return resultOptional;
	}

	// Eliminare tramite Id
	public void deleteById(Long id) {
		if (!athleteRepo.existsById(id)) {
			throw new EntityNotFoundException("Athlete not found");
		}
		athleteRepo.deleteById(id);
		log.info("Athlete deleted"); 
	}

	// Metodo per Salvataggio Atleta
	public Athlete insertAthlete(AthleteDto objectToInsert) throws Exception {
		// verifico se già esiste il codice Fiscale. Se esiste mando eccezione
		if (athleteRepo.existsByFiscalCode(objectToInsert.getFiscalCode())) {
			throw new EntityExistsException("Athlete already exists");
		}else if(objectToInsert.getName().length()==0) {
			throw new Exception("Name field is empty");
		}else if(objectToInsert.getSurname().length()==0) {
			throw new Exception("Surname field is empty");
		}else if(objectToInsert.getEmail().length()==0) {
			throw new Exception("Email field is empty");
		}else if(objectToInsert.getFiscalCode().length()==0 || objectToInsert.getFiscalCode().length()!=16) {
			throw new Exception("FiscalCode field is empty or incorrect");
		}else if(objectToInsert.getNTel().length()==0) {
			throw new Exception("Number Telephone field is empty");
		}else if(objectToInsert.getAddress().length()==0) {
			throw new Exception("Address field is empty");
		}else if(!objectToInsert.getCap().matches("^[0-9]{5}$")) {
			throw new Exception("Cap field is empty or wrong. You need to insert 5 number");
		}
		log.info("Inserting Athlete: {}", objectToInsert);
		Athlete result = new Athlete();
		// copio le proprietà del dto nell'entity principale
		BeanUtils.copyProperties(objectToInsert, result);

		athleteRepo.save(result);
		log.info("Inserted Athlete: {}", result);
		return result;
	}
	
	//Metodo per modificare Dati atleta
	public Athlete update(Long id, AthleteDto dto) throws Exception {
		//verifico se esiste l'utente con id che passo
		if (!athleteRepo.existsById(id)) {
			throw new EntityNotFoundException("Athlete Not Found");
		}else if(dto.getName().length()==0) {
			throw new Exception("Name field is empty");
		}else if(dto.getSurname().length()==0) {
			throw new Exception("Surname field is empty");
		}else if(dto.getEmail().length()==0) {
			throw new Exception("Email field is empty");
		}else if(dto.getFiscalCode().length()==0 || dto.getFiscalCode().length()!=16) {
			throw new Exception("FiscalCode field is empty or incorrect");
		}else if(dto.getNTel().length()==0) {
			throw new Exception("Number Telephone field is empty");
		}else if(dto.getAddress().length()==0) {
			throw new Exception("Address field is empty");
		}else if(!dto.getCap().matches("^[0-9]{5}$")) {
			throw new Exception("Cap field is empty or wrong. You need to insert 5 number");
		}
		//ottengo l'oggetto che voglio tramite id
		Athlete athlete = getById(id).get();
		//copio le proprietà dto nell'entity principale
		BeanUtils.copyProperties(dto, athlete);
		//salvo nel db
		return athleteRepo.save(athlete);
	}


}
