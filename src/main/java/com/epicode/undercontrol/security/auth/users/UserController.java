package com.epicode.undercontrol.security.auth.users;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epicode.undercontrol.errors.UserExceptionNotValid;
import com.epicode.undercontrol.teams.Team;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService service;

	/**
	 * Questo metodo inserisce un nuovo oggetto - Admin nel sistema
	 */
	@PostMapping("/insertAdmin")
	@PreAuthorize("hasRole('DEVELOPER')")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<User> insertAdmin(@RequestBody UserDto objectToInsert) throws UserExceptionNotValid {
		log.info("Called insert for object: {}", objectToInsert);
		return ResponseEntity.ok(service.insertAdmin(objectToInsert));
	}

	/**
	 * Questo metodo inserisce un nuovo oggetto - User nel sistema
	 */
	@PostMapping("/insertUser")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<User> insertUser(@RequestBody UserDto objectToInsert) throws UserExceptionNotValid {
		log.info("Called insert for object: {}", objectToInsert);
		return ResponseEntity.ok(service.insertUser(objectToInsert));
	}

	@GetMapping("/getByUsername")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<?> getByUsername(@RequestParam(name = "username") String username,
			@RequestParam(name = "ignorecase", required = false) Boolean ignoreCase) {
		log.info("Called find");
		Optional<User> byName = service.getByUsername(username, ignoreCase != null ? ignoreCase : true);
		if (byName.isPresent()) {
			return new ResponseEntity(byName.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity("EpicodeUser with name: " + username + " not found", HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Questo metodo ritorna una lista di tutti gli oggetti presenti nel sistema
	 */
	@GetMapping("/findAll")
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
			List<User> list = service.findAll();
			return new ResponseEntity(list, HttpStatus.OK);
		} else {
			log.info("Called findAll");
			List<User> list = service.findAll();
			List<User> listTeam = list.stream().filter(c -> c.getSociety().equalsIgnoreCase(societa))
					.collect(Collectors.toList());
			return new ResponseEntity(listTeam, HttpStatus.OK);
		}

	}

	/**
	 * Questo metodo ritorna un oggetto presente nel sistema con id indicato
	 * 
	 * La chiamata è nella forma GET /devicetype/{id} es. GET /devicetype/1
	 */
	@GetMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<User> findById(@PathVariable Long id) {
		log.info("Called findById for id: {}", id);
		return ResponseEntity.ok(service.getById(id));
	}

	/**
	 * Questo metodo elimina un oggetto dal sistema in base all'id selezionato
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<?> delete(@PathVariable Long id) {
		log.info("Called delete for object: {}", id);
		try {
			service.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			log.info("No User entity with id " + id + " exists!");
		}
		return new ResponseEntity(HttpStatus.OK);
	}

	/**
	 * Questo metodo aggiorna un oggetto già presente nel sistema
	 */
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<?> update(@RequestBody UserDto dto, @PathVariable Long id) {
		log.info("Called update for object: {}", service.getById(id));
		return ResponseEntity.ok(service.update(id, dto));

	}
}
