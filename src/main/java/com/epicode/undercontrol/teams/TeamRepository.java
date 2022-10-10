package com.epicode.undercontrol.teams;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface TeamRepository extends PagingAndSortingRepository<Team, Long> {
public Team findBySeason(String season);
public Team findByName(String season);
}
