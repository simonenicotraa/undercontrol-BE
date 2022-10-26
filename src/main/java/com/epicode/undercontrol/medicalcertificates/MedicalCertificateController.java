package com.epicode.undercontrol.medicalcertificates;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins="*")
@Slf4j
@RequestMapping("/certificates")
public class MedicalCertificateController {
	@Autowired
private MedicalCertificateService service;
	/**
	 * Questo metodo ritorna una lista di tutti gli oggetti presenti nel sistema
	 */
	@GetMapping("/findAllMedicalCertificate")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<?> findAll() {
		log.info("Called findAll MedicalCertificate");
		List<MedicalCertificate> listUser = service.findAll();
		return new ResponseEntity(listUser, HttpStatus.OK);
	}

	/**
	 * Questo metodo inserisce un nuovo oggetto nel sistema
	 */
	@PostMapping("/insertMedicalCertificate{id}")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<MedicalCertificate> insert(@RequestBody MedicalCertificateDto objectToInsert,@PathVariable Long id) {
		log.info("Called insert for MedicalCertificate: {}", objectToInsert);
		return ResponseEntity.ok(service.insert(objectToInsert,id));
	}

	/**
	 * Questo metodo elimina un oggetto dal sistema in base all'id selezionato
	 */
	@DeleteMapping("/{id}/{idAthl}")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<?> delete(@PathVariable Long id,@PathVariable Long idAthl) {
		log.info("Called delete for MedicalCertificate: {}", id);
		service.deleteById(id, idAthl);
		return ResponseEntity.ok("MedicalCertificate with id: " + id + " deleted");
	}

	/**
	 * Questo metodo aggiorna un MedicalCertificate già presente nel sistema
	 */
	@PutMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<?> update(@RequestBody MedicalCertificateDto dto, @PathVariable Long id) {
		log.info("Called update for MedicalCertificate: {}", service.getById(id));
		return ResponseEntity.ok(service.update(id, dto));
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
		Optional<MedicalCertificate> objectFound = service.getById(id);
		if (objectFound.isPresent()) {
			return new ResponseEntity<>(objectFound.get(), HttpStatus.FOUND);
		} else {
			return new ResponseEntity("MedicalCertificate with id: " + id + " not found", HttpStatus.NOT_FOUND);
		}
	}
}
