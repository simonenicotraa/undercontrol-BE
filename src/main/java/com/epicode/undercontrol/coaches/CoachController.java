package com.epicode.undercontrol.coaches;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins="*")
@Slf4j
@RequestMapping("/coaches")
public class CoachController {
	@Autowired
	private CoachService service;

	/**
	 * Questo metodo ritorna una lista di tutti gli Coach presenti nel sistema
	 */
	@GetMapping("/findAllCoaches")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<?> findAll(@RequestHeader(name = "Authorization") String token) {
		// REQUEST HEADER PER OTTENERE DATI DALL'HEADER DEL TOKEN
				// log.info(token); log.info(token.replace("Bearer ", ""));
				String tokenClean = token.replace("Bearer ", "");
				// decodifico il token per vedere da cosa è composto
				String[] chunks = tokenClean.split("\\.");
				Base64.Decoder decoder = Base64.getUrlDecoder();
				String header = new String(decoder.decode(chunks[0]));
				String payload = new String(decoder.decode(chunks[1]));
				// System.out.println(payload);
				// seleziono le parti del payload
				String[] payloadPart = payload.split(",");
				// System.out.println(payloadPart[1]);
				// accedo al dato che mi interessa --> società
				String[] society = payloadPart[1].split(":");
				// levo i doppi apici e li rimpiazzo con uno spazio che successivamente levo con
				// trim
				String societa = society[1].replace('"', ' ').trim();
				System.out.println(societa);
				//se il team è undercontrol (nome team del developer) allora mostra tutto
				if (societa.equalsIgnoreCase("UnderControl")) {
					log.info("Called findAll");
					List<Coach> list = service.findAll();
					return new ResponseEntity(list, HttpStatus.OK);
				} else {
					log.info("Called findAll");
					List<Coach> list = service.findAll();
					List<Coach> listTeam = list.stream().filter(c -> c.getSociety().equalsIgnoreCase(societa))
							.collect(Collectors.toList());
					return new ResponseEntity(listTeam, HttpStatus.OK);
				}
	}

	/**
	 * Questo metodo inserisce un nuovo Coach nel sistema
	 */
	@PostMapping("/insertCoach")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<Coach> insertCoach(@RequestBody CoachDto objectToInsert) throws UserExceptionNotValid,Exception {
		log.info("Called insert for object: {}", objectToInsert);
		try{
			return ResponseEntity.ok(service.insertCoach(objectToInsert));
		}catch (Exception e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.OK);
		}
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
		return new ResponseEntity("User with id: " + id + " deleted", HttpStatus.OK);
	}

	/**
	 * Questo metodo aggiorna un atleta già presente nel sistema
	 * @throws Exception 
	 */
	@PutMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<?> update(@RequestBody CoachDto dto, @PathVariable Long id) throws Exception {
		log.info("Called update for object: {}", service.getById(id));
		try{
			return ResponseEntity.ok(service.update(id, dto));
		}catch (Exception e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.OK);
		}
	}
	/**
	 * Questo metodo ritorna un oggetto presente nel sistema con id indicato
	 * La chiamata è nella forma GET /devicetype/{id} es. GET /devicetype/1
	 */
	@GetMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<?> findById(@PathVariable Long id) {
		log.info("Called findById for id: {}", id);
		Optional<Coach> objectFound = service.getById(id);
		if (objectFound.isPresent()) {
			return new ResponseEntity<>(objectFound.get(), HttpStatus.FOUND);
		} else {
			return new ResponseEntity("Athlete with id: " + id + " not found", HttpStatus.NOT_FOUND);
		}
	}
}
