package com.epicode.undercontrol.payments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDtoComplete {
	private String season;
	private Integer amount;
	private Integer payed;
}
