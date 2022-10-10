package com.epicode.undercontrol.teams;

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
import com.epicode.undercontrol.security.auth.users.User;
import com.epicode.undercontrol.security.auth.users.UserDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
@RequestMapping("/team")
public class TeamController {
	@Autowired
	private TeamService service;

	/**
	 * Questo metodo ritorna una lista di tutti i Team presenti nel sistema
	 */
	@GetMapping("/findAllTeams")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<?> findAll() {
		log.info("Called findAll");
		List<Team> listTeam = service.findAll();
		return new ResponseEntity(listTeam, HttpStatus.OK);
	}

	/**
	 * Questo metodo inserisce un nuovo Team nel sistema
	 */
	@PostMapping("/insertTeam")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<Team> insert(@RequestBody TeamDto objectToInsert) throws UserExceptionNotValid {
		log.info("Called insert for object: {}", objectToInsert);
		return ResponseEntity.ok(service.insert(objectToInsert));
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
			log.info("No Team entity with id " + id + " exists!");
		}
		return new ResponseEntity("Team with id: " + id + " deleted", HttpStatus.OK);
	}

	/**
	 * Questo metodo aggiorna, le proprieta presenti su teamDto un Team già presente nel sistema 
	 */
	@PutMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<?> update(@RequestBody TeamDto dto, @PathVariable Long id) {
		log.info("Called update for Team: {}", service.getById(id));
		return ResponseEntity.ok(service.update(id, dto));
	}

	/**
	 * Questo metodo ritorna un oggetto presente nel sistema con id indicato La
	 * chiamata è nella forma GET /devicetype/{id} es. GET /devicetype/1
	 */
	@GetMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<?> findById(@PathVariable Long id) {
		log.info("Called findById for id: {}", id);
		return ResponseEntity.ok(service.getById(id));
	}

	
	/**
	 * Questi due metodi servono per aggiornare le liste di coach e atleti presenti
	 * all'interno della classe Team. Hanno due modi di operarare differenti.
	 * Entrambi funzionanti
	 */
	@PatchMapping("/updateListAtl/{id}")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<Team> patchListAthl(@PathVariable Long id, @RequestBody TeamDtoListAthletes dto) {
		return ResponseEntity.ok(service.updateAthleteList(id, dto));
	}

	@PatchMapping("/updateListCoach/{idTeam}/{idCoach}")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<Team> patchListCoach(@PathVariable Long idTeam, @PathVariable Long idCoach) {
		return ResponseEntity.ok(service.updateCoachList(idTeam, idCoach));
	}

}
