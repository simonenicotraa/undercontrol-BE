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
	public Payment insert(PaymentDto objectToInsert, Long id) {
		if (payRepo.existsById(id)) {
			throw new EntityExistsException("Payment Already exists");
		}
		log.info("Inserting Payment: {}", objectToInsert);
		//prendo l'atleta per id
		Athlete a = athService.getById(id).get();
		Payment payment = new Payment();
		// copio le proprietà del dto nell'entity principale
		BeanUtils.copyProperties(objectToInsert, payment);
		//aggiungo il pagamento alla lista pagamenti dell'atleta
		a.addPayment(payment);
		payRepo.save(payment);
		log.info("Inserted Payment: {}", payment);
		return payment;
	}

	public Payment update(Long id, PaymentDtoComplete dto) {
		if (!payRepo.existsById(id)) {
			throw new EntityNotFoundException("Payment not found");
		}
		Payment p = payRepo.findById(id).get();
		// verifico se è nullo perchè se inizialmente non inserisco nessun valore il
		// getPayed sarà null
		if (p.getPayed() != null) {
			dto.setPayed(p.getPayed() + dto.getPayed());
		}

		BeanUtils.copyProperties(dto, p);
		return payRepo.save(p);
	}

}
