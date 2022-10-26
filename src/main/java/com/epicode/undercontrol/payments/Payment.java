package com.epicode.undercontrol.payments;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@NoArgsConstructor
@Slf4j
@AllArgsConstructor
@Data
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String season;
	private Integer amount;
	private Integer payed;
	private boolean paymentStatus;
	
	public void reviewPaymentStatus() {
		if(amount.equals(payed)) {
			paymentStatus= true;
			log.info("prova vero" );
		}else {
			paymentStatus=false;
			log.info("prova falso" );
		}
	}
}
