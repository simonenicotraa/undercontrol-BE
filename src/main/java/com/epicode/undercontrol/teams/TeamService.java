package com.epicode.undercontrol.teams;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.epicode.undercontrol.athletes.Athlete;
import com.epicode.undercontrol.athletes.AthleteService;
import com.epicode.undercontrol.coaches.Coach;
import com.epicode.undercontrol.coaches.CoachService;
import com.epicode.undercontrol.security.auth.users.User;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class TeamService {
	private TeamRepository repo;
	private CoachService coachService;
	private AthleteService athleteService;

	// metodo per restituire tutti gli atleti
	public List<Team> findAll() {
		List<Team> findAll = (List<Team>) repo.findAll();
		log.info("Found {} Team", findAll.size());
		return findAll;
	}

	public Team getById(Long id) {
		log.info("Finding object: id {}", id);
		Team result = repo.findById(id).get();
		if (repo.existsById(id)) {
			log.info("Found object: id {}", id);
		} else {
			log.info("Not found object: id {}", id);
		}
		return result;
	}

	// Eliminare tramite Id
	public void deleteById(Long id) {
		if (!repo.existsById(id)) {
			throw new EntityNotFoundException("Team not found");
		}
		repo.deleteById(id);
		log.info("Team deleted");
	}

	// Metodo per Salvataggio Team
	public Team insert(TeamDto objectToInsert) {
		log.info("Inserting Athlete: {}", objectToInsert);
		Team result = new Team();
		// copio le proprietà del dto nell'entity principale
		BeanUtils.copyProperties(objectToInsert, result);

		repo.save(result);
		log.info("Inserted Team: {}", result);
		return result;
	}

	// Metodo per modificare Dati Team
	public Team update(Long id, TeamDto dto) {
		// verifico se esiste Team con id che passo
		if (!repo.existsById(id)) {
			throw new EntityNotFoundException("Team Not Found");
		}
		// ottengo l'oggetto che voglio tramite id
		Team team = getById(id);
		// copio le proprietà dto nell'entity principale
		BeanUtils.copyProperties(dto, team);
		// salvo nel db
		return repo.save(team);
	}

	public Team update(Long id, Team t) {
		// verifico se esiste Team con id che passo
		if (!repo.existsById(id)) {
			throw new EntityNotFoundException("Team Not Found");
		}
		// ottengo l'oggetto che voglio tramite id
		Team team = getById(id);

		return repo.save(team);
	}
	
	//metodi diversi ma che svolgono la stessa funzione
	//aggiunge atleta alla lista del team
	public Team updateAddAthleteList(Long id, TeamDtoListAthletes dto) {
		// verifico se esiste Team con id che passo
		if (!repo.existsById(id)) {
			throw new EntityNotFoundException("Team Not Found");
		}
		// ottengo l'oggetto che voglio tramite id
		Team team = getById(id);
		BeanUtils.copyProperties(dto, team);
		return repo.save(team);
	}
	//aggiunge coach alla lista coach del team
	public Team updateAddCoachList(Long teamid, Long coachId) {
		// verifico se esiste Team con id che passo
		if (!repo.existsById(teamid)) {
			throw new EntityNotFoundException("Team Not Found");
		}
		Team t = repo.findById(teamid).get();
		Coach c = coachService.getById(coachId).get();
		t.addCoach(c);
		return repo.save(t);
	}

	// rimuovere atleta dalla lista del team
	public Team updateAthleteRemoveList(Long teamid, Long athleteId) {
		// verifico se esiste Team con id che passo
		if (!repo.existsById(teamid)) {
			throw new EntityNotFoundException("Team Not Found");
		}
		Team t = repo.findById(teamid).get();
		Athlete a = athleteService.getById(athleteId).get();
		t.removeAthlete(a);
		return repo.save(t);
	}

	// rimuovere coach dalla lista coach del team
	public Team updateCoachRemoveList(Long teamid, Long coachId) {
		// verifico se esiste Team con id che passo
		if (!repo.existsById(teamid)) {
			throw new EntityNotFoundException("Team Not Found");
		}
		Team t = repo.findById(teamid).get();
		Coach c = coachService.getById(coachId).get();
		t.removeCoach(c);
		return repo.save(t);
	}

}
