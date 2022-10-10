package com.epicode.undercontrol.coaches;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoachRepository extends PagingAndSortingRepository<Coach, Long> {

	public Optional<Coach> findByFiscalCodeIgnoreCase(String fiscalCode);
	public boolean existsByFiscalCode(String fiscalCode);
}
