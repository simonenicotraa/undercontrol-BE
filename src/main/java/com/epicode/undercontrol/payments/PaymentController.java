package com.epicode.undercontrol.payments;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins="*")
@Slf4j
@RequestMapping("/payment")
public class PaymentController {
	@Autowired
	private PaymentService service;
	
	/**
	 * Questo metodo ritorna una lista di tutti gli oggetti presenti nel sistema
	 */
	@GetMapping("/findAllPayments")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<?> findAll() {
		log.info("Called findAll Payment");
		List<Payment> listPayments = service.findAll();
		return new ResponseEntity(listPayments, HttpStatus.OK);
	}
	
	/**
	 * Questo metodo inserisce un nuovo oggetto nel sistema
	 */
	@PostMapping("/insertPayment{id}")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<Payment> insert(@RequestBody PaymentDto objectToInsert,@PathVariable Long id) {
		log.info("Called insert for Payment: {}", objectToInsert);
		return ResponseEntity.ok(service.insert(objectToInsert,id));
	}
	
	/**
	 * Questo metodo elimina un oggetto dal sistema in base all'id selezionato
	 */
	@DeleteMapping("/{id}/{idAthl}")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<?> delete(@PathVariable Long id, @PathVariable Long idAthl) {
			log.info("Called delete for object: {}", id);
			try {
				service.deleteById(id,idAthl);
			} catch (EmptyResultDataAccessException e) {
				log.info("No User entity with id " + id + " exists!");
			} 
			return new ResponseEntity(HttpStatus.OK);
		}
	
	
	/**
	 * Questo metodo ritorna un oggetto presente nel sistema con id indicato
	 * La chiamata è nella forma GET /devicetype/{id} es. GET /devicetype/1
	 */
	@GetMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<Payment> findById(@PathVariable Long id) {
		log.info("Called findById for id: {}", id);
		return ResponseEntity.ok(service.getById(id).get());
	}
	
	@PutMapping("/update/{id}")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<?> update(@PathVariable Long id,@RequestBody PaymentDtoComplete dto){
		return ResponseEntity.ok(service.update(id, dto));
	}
	
	
	 
	
}
