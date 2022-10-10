package com.epicode.undercontrol.coacheslevel;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoachLevelRepository extends PagingAndSortingRepository<CoachLevel, Long> {
	public boolean existsByLevel(ECoachLevel level);
}
