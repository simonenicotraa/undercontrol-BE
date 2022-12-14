package com.epicode.undercontrol.payments;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epicode.undercontrol.athletes.Athlete;
import com.epicode.undercontrol.athletes.AthleteService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentService {
	@Autowired
	private PaymentRepository payRepo;
	@Autowired
	private AthleteService athService;

	// metodo per restituire tutti gli oggett
	public List<Payment> findAll() {
		List<Payment> findAll = (List<Payment>) payRepo.findAll();
		log.info("Found {} Payment", findAll.size());
		for (Payment payment : findAll) {
			payment.reviewPaymentStatus();
		}
		payRepo.saveAll(findAll);
		return findAll;
	}

	// Ottenere oggetti tramite Id
	public Optional<Payment> getById(Long id) {
		log.info("Finding object: id {}", id);
		Optional<Payment> resultOptional = payRepo.findById(id);
		if (resultOptional.isPresent()) {
			log.info("Found Payment: id {}", id);
		} else {
			log.info("Not found Payment: id {}", id);
		}
		return resultOptional;
	}

	// Eliminare tramite oggetti Id
	public void deleteById(Long id ,Long idAthl) {
		if (!payRepo.existsById(id)) {
			throw new EntityNotFoundException("Payment not found");
		}
		Athlete a = athService.getById(idAthl).get();
		Payment p = payRepo.getById(id);
		a.removePayment(p);
		payRepo.deleteById(id);
		log.info("Payment deleted");
	}
 
	// Metodo per Salvataggio oggetti
	public Payment insert(PaymentDto objectToInsert, Long id) throws Exception {
		if (payRepo.existsById(id)) {
			throw new EntityExistsException("Payment Already exists");
		}else if(objectToInsert.getSeason().length()==0){
			throw new Exception("Insert the season");
		}else if(objectToInsert.getAmount()==null) {
			throw new Exception("Insert the Amount");
		}
		log.info("Inserting Payment: {}", objectToInsert);
		//prendo l'atleta per id
		Athlete a = athService.getById(id).get();
		Payment payment = new Payment();
		// copio le propriet?? del dto nell'entity principale
		BeanUtils.copyProperties(objectToInsert, payment);
		//aggiungo il pagamento alla lista pagamenti dell'atleta
		a.addPayment(payment);
		payment.setIdathlete(a.getId());
		payment.reviewPaymentStatus();
		payRepo.save(payment);
		log.info("Inserted Payment: {}", payment);
		return payment;
	}

	public Payment update(Long id, PaymentDtoComplete dto) throws Exception {
		if (!payRepo.existsById(id)) {
			throw new EntityNotFoundException("Payment not found");
		} else if(dto.getSeason().length()==0){
			throw new Exception("Insert the season");
		}else if(dto.getAmount()==null) {
			throw new Exception("Insert the Amount");
		}else if(dto.getPayed()==null) {
			throw new Exception("Insert how much athlete payed");
		}
		Payment p = payRepo.findById(id).get();
		// verifico se ?? nullo perch?? se inizialmente non inserisco nessun valore il
		// getPayed sar?? null
		if (p.getPayed() != null ) {			
			
			if((p.getPayed() + dto.getPayed()) > p.getAmount()){
				throw new Exception("Payment has exceeded total amount");	
			}	
				else dto.setPayed(p.getPayed() + dto.getPayed());
		} 
		if(p.getPayed() ==null && dto.getPayed()>p.getAmount()) {
			throw new Exception("Payment has exceeded total amount");
		}
		BeanUtils.copyProperties(dto, p);
		return payRepo.save(p);
	}

}
