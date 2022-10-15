package com.epicode.undercontrol.teams;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epicode.undercontrol.athletes.Athlete;
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
	public ResponseEntity<?> findAll(@RequestHeader(name = "Authorization") String token) {
		//REQUEST HEADER PER OTTENERE DATI DALL'HEADER DEL TOKEN
				//log.info(token);	log.info(token.replace("Bearer ", ""));
				String tokenClean = token.replace("Bearer ", "");
				//decodifico il token per vedere da cosa è composto	
						String[] chunks = tokenClean.split("\\.");
						Base64.Decoder decoder = Base64.getUrlDecoder();
						String header = new String(decoder.decode(chunks[0]));
						String payload = new String(decoder.decode(chunks[1]));
						//System.out.println(payload);
						//seleziono le parti del payload
						String[] payloadPart = payload.split(",");				
						//System.out.println(payloadPart[1]);
						//accedo al dato che mi interessa --> società
						String[] society = payloadPart[1].split(":");
						//levo i doppi apici e li rimpiazzo con uno spazio che successivamente levo con trim
						String societa = society[1].replace('"', ' ').trim();
						//System.out.println(societa);
						//se il team è undercontrol (nome team del developer) allora mostra tutto
						if (societa.equalsIgnoreCase("UnderControl")) {
							log.info("Called findAll");
							List<Team> list = service.findAll();
							return new ResponseEntity(list, HttpStatus.OK);
						} else {
							log.info("Called findAll");
							List<Team> list = service.findAll();
							List<Team> listTeam = list.stream().filter(c -> c.getSociety().equalsIgnoreCase(societa))
									.collect(Collectors.toList());
							return new ResponseEntity(listTeam, HttpStatus.OK);
						}
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
	 * Questi due metodi servono per aggiornare (AGGIUNGERE) le liste di coach e atleti presenti
	 * all'interno della classe Team. Hanno due modi di operare differenti.
	 * Entrambi funzionanti
	 */
	@PatchMapping("/updateListAtl/{id}")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<Team> patchListAddAthl(@PathVariable Long id, @RequestBody TeamDtoListAthletes dto) {
		return ResponseEntity.ok(service.updateAddAthleteList(id, dto));
	}

	@PatchMapping("/updateListCoach/{idTeam}/{idCoach}")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<Team> patchListAddCoach(@PathVariable Long idTeam, @PathVariable Long idCoach) {
		return ResponseEntity.ok(service.updateAddCoachList(idTeam, idCoach));
	}
	
	/**
	 * Questi due metodi servono per aggiornare (RIMUOVERE) le liste di coach e atleti presenti
	 * all'interno della classe Team. Hanno due modi di operare differenti.
	 * Entrambi funzionanti
	 */
	@PatchMapping("/updateListRemoveAtl/{idTeam}/{idAthlete}")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<Team> patchListRemoveAthl(@PathVariable Long idTeam, @PathVariable Long idAthlete) {
		return ResponseEntity.ok(service.updateAthleteRemoveList(idTeam, idAthlete));
	}

	@PatchMapping("/updateListRemoveCoach/{idTeam}/{idCoach}")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<Team> patchListRemoveCoach(@PathVariable Long idTeam, @PathVariable Long idCoach) {
		return ResponseEntity.ok(service.updateCoachRemoveList(idTeam, idCoach));
	}
}
