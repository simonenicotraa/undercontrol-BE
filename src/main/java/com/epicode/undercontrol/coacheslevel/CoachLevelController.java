package com.epicode.undercontrol.coacheslevel;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epicode.undercontrol.athletes.Athlete;
import com.epicode.undercontrol.athletes.AthleteDto;
import com.epicode.undercontrol.errors.UserExceptionNotValid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/coachlevel")
@AllArgsConstructor
public class CoachLevelController {
private CoachLevelService service;

@GetMapping("/findAllLevels")
@PreAuthorize("isAuthenticated()")
@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
public ResponseEntity<?> findAll() {
	log.info("Called findAll");
	List<CoachLevel> listUser = service.findAll();
	return new ResponseEntity(listUser, HttpStatus.OK);
}

/**
 * Questo metodo inserisce un nuovo livello nel sistema
 */
@PostMapping("/insertLevel")
@PreAuthorize("isAuthenticated()")
@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
public ResponseEntity<CoachLevel> insertAdmin(@RequestBody CoachLevelDto objectToInsert) {
	log.info("Called insert for object: {}", objectToInsert);
	return ResponseEntity.ok(service.insertLevel(objectToInsert));
}

/**
 * Questo metodo elimina un livello dal sistema in base all'id selezionato
 */
@DeleteMapping("/{id}")
@PreAuthorize("isAuthenticated()")
@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
public ResponseEntity<?> delete(@PathVariable Long id) {
	log.info("Called delete for object: {}", id);
	try {
		service.delete(id);
	} catch (EmptyResultDataAccessException e) {
		log.info("No CoachLevel entity with id " + id + " exists!");
	}
	return new ResponseEntity("CoachLevel with id: " + id + " deleted",HttpStatus.OK);
}

@GetMapping("/{id}")
@PreAuthorize("isAuthenticated()")
@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
public ResponseEntity<?> findById(@PathVariable Long id) {
	log.info("Called findById for id: {}", id);
	Optional<CoachLevel> objectFound = service.getById(id);
	if (objectFound.isPresent()) {
		return new ResponseEntity<>(objectFound.get(), HttpStatus.FOUND);
	} else {
		return new ResponseEntity("CoachLevel with id: " + id + " not found", HttpStatus.NOT_FOUND);
	}
}




}
