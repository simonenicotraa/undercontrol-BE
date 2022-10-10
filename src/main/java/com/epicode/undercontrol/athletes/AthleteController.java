package com.epicode.undercontrol.athletes;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epicode.undercontrol.errors.UserExceptionNotValid;
import com.epicode.undercontrol.medicalcertificates.MedicalCertificate;
import com.epicode.undercontrol.medicalcertificates.MedicalCertificateDto;
import com.epicode.undercontrol.security.auth.users.User;
import com.epicode.undercontrol.security.auth.users.UserDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins="*")
@Slf4j
@RequestMapping("/athlete")
public class AthleteController {
	@Autowired
	private AthleteService service;

	/**
	 * Questo metodo ritorna una lista di tutti gli Atleti presenti nel sistema
	 */
	@GetMapping("/findAllAthletes")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<?> findAll() {
		log.info("Called findAll");
		List<Athlete> listUser = service.findAll();
		return new ResponseEntity(listUser, HttpStatus.OK);
	}

	/**
	 * Questo metodo inserisce un nuovo Atleta nel sistema
	 */
	@PostMapping("/insertAthletes")
//	@PreAuthorize("isAuthenticated()")
//	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<Athlete> insert(@RequestBody AthleteDto objectToInsert) throws UserExceptionNotValid {
		log.info("Called insert for object: {}", objectToInsert);
		return ResponseEntity.ok(service.insertAthlete(objectToInsert));
	}

	/**
	 * Questo metodo elimina un oggetto dal sistema in base all'id selezionato
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<?> delete(@PathVariable Long id) {
		log.info("Called delete for object: {}", id);
		try {
			service.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			log.info("No User entity with id " + id + " exists!");
		}
		return new ResponseEntity("User with id: " + id + " deleted",HttpStatus.OK);
	}

	/**
	 * Questo metodo aggiorna un atleta già presente nel sistema
	 */
	@PutMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<?> update(@RequestBody AthleteDto dto, @PathVariable Long id) {
		log.info("Called update for object: {}", service.getById(id));
		return ResponseEntity.ok(service.update(id, dto));
	}
	/**
	 * Questo metodo ritorna un oggetto presente nel sistema con id indicato
	 * 
	 * La chiamata è nella forma GET /devicetype/{id} es. GET /devicetype/1
	 */
	@GetMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<?> findById(@PathVariable Long id) {
		log.info("Called findById for id: {}", id);
		Optional<Athlete> objectFound = service.getById(id);
		if (objectFound.isPresent()) {
			return new ResponseEntity<>(objectFound.get(), HttpStatus.FOUND);
		} else {
			return new ResponseEntity("Athlete with id: " + id + " not found", HttpStatus.NOT_FOUND);
		}
	}
	

}
