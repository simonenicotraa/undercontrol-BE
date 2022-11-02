package com.epicode.undercontrol.coaches;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class CoachService {
	private CoachRepository athleteRepo;

	// metodo per restituire tutti gli Coach
	public List<Coach> findAll() {
		List<Coach> findAll = (List<Coach>) athleteRepo.findAll();
		log.info("Found {} Coach", findAll.size());
		return findAll;
	}

	// Ottenere Coach tramite username - NO Case Sensitive
	public Optional<Coach> getByFiscalCode(String name, boolean ignoreCase) {
		log.info("Finding Coach: name {} with ignoreCase {}", name, ignoreCase);
		Optional<Coach> resultOptional = Optional.empty();
		if (ignoreCase) {
			resultOptional = athleteRepo.findByFiscalCodeIgnoreCase(name);
		} else {
			resultOptional = athleteRepo.findByFiscalCodeIgnoreCase(name);
		}
		if (resultOptional.isPresent()) {
			log.info("Found Coach: name {}", name);
		} else {
			log.info("Not found Coach: name {}", name);
		}
		return resultOptional;
	}

	// Ottenere Coach tramite Id
	public Optional<Coach> getById(Long id) {
		log.info("Finding object: id {}", id);
		Optional<Coach> resultOptional = athleteRepo.findById(id);
		if (resultOptional.isPresent()) {
			log.info("Found Coach: id {}", id);
		} else {
			log.info("Not found Coach: id {}", id);
		}
		return resultOptional;
	}

	// Eliminare tramite Coach Id
	public void deleteById(Long id) {
		if (!athleteRepo.existsById(id)) {
			throw new EntityNotFoundException("Coach not found");
		}
		athleteRepo.deleteById(id);
		log.info("Coach deleted");
	}

	// Metodo per Salvataggio Coach
	public Coach insertCoach(CoachDto objectToInsert) throws Exception {
		// verifico se già esiste il codice Fiscale. Se esiste mando eccezione
		if (athleteRepo.existsByFiscalCode(objectToInsert.getFiscalCode())) {
			throw new EntityExistsException("Coach already exists");
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
			throw new Exception("Cap field is empty or wrong. (You need to insert 5 number)");
		}
		log.info("Inserting Coach: {}", objectToInsert);
		Coach coach = new Coach();
		// copio le proprietà del dto nell'entity principale
		BeanUtils.copyProperties(objectToInsert, coach);

		athleteRepo.save(coach);
		log.info("Inserted Coach: {}", coach);
		return coach;
	}
	
	//Metodo per modificare Dati atleta
	public Coach update(Long id, CoachDto dto) throws Exception {
		//verifico se esiste l'utente con id che passo
		if (!athleteRepo.existsById(id)) {
			throw new EntityNotFoundException("Coach Not Found");
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
			throw new Exception("Cap field is empty or wrong. (You need to insert 5 number)");
		}
		//ottengo l'oggetto che voglio tramite id
		Coach coach = getById(id).get();
		//copio le proprietà dto nell'entity principale
		BeanUtils.copyProperties(dto, coach);
		//salvo nel db
		return athleteRepo.save(coach);
	}

}
