package com.epicode.undercontrol.payments;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.epicode.undercontrol.athletes.Athlete;

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
	
	private Long Idathlete;
	
	private String society;
	public void reviewPaymentStatus() {
		if(amount.equals(payed)) {
			paymentStatus= true;
			//log.info("prova vero" );
		}else {
			paymentStatus=false;
			//log.info("prova falso" );
		}
	}
}
