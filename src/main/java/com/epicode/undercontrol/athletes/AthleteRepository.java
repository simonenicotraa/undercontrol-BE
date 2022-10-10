package com.epicode.undercontrol.athletes;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AthleteRepository extends PagingAndSortingRepository<Athlete, Long> {

	public Optional<Athlete> findByFiscalCodeIgnoreCase(String fiscalCode);
	public boolean existsByFiscalCode(String fiscalCode);
}
