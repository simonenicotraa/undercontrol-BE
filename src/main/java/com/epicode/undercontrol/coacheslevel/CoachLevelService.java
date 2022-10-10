package com.epicode.undercontrol.coacheslevel;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class CoachLevelService {
	private CoachLevelRepository repo;

	public List<CoachLevel> findAll() {
		List<CoachLevel> levels = (List<CoachLevel>) repo.findAll();
		log.info("Found {} Athletes", levels.size());
		return levels;
	}

	// Ottenere LivelloCoach tramite Id
	public Optional<CoachLevel> getById(Long id) {
		log.info("Finding object: id {}", id);
		Optional<CoachLevel> coachLevel = repo.findById(id);
		if (coachLevel.isPresent()) {
			log.info("Found object: id {}", id);
		} else {
			log.info("Not found object: id {}", id);
		}
		return coachLevel;
	}
	
	//eliminare livelloCoach tramite Id
	public void delete(Long id) {
		if (!repo.existsById(id)) {
			throw new EntityNotFoundException("Athlete not found");
		}
		repo.deleteById(id);
		log.info("Athlete deleted");
	}
	
	//Inserire LivelloCoach 
	public CoachLevel insertLevel(CoachLevelDto dto) {
		if (repo.existsByLevel(dto.getLevel())) {
			throw new EntityExistsException("Level already in use");
		}
		CoachLevel level = new CoachLevel();
		BeanUtils.copyProperties(dto, level);
		return repo.save(level);
	}
}
