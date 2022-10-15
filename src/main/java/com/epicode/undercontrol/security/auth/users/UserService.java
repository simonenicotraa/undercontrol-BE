package com.epicode.undercontrol.security.auth.users;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.epicode.undercontrol.errors.UserExceptionNotValid;
import com.epicode.undercontrol.security.auth.roles.Role;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired	@Qualifier("admin")	Role admin;
	@Autowired	@Qualifier("user")	Role user;
	@Autowired	@Qualifier("developer")	Role developer;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<UserResponse> getAllUsersBasicInformations() {
		return userRepository.findAll().stream()
				.map(user -> UserResponse.builder().userName(user.getUsername())
						.role(user.getRoles().stream().findFirst().get().getRoleName().name()).build())
				.collect(Collectors.toList());
	}

	public UserResponse getUserBasicInformations(String userName) {
		User user = userRepository.findByUsername(userName).get();

		return UserResponse.builder().userName(userName)
				.role(user.getRoles().stream().findFirst().get().getRoleName().name()).build();

	}
	
	//Metodo per restituire tutti gli USER nel sistema
	public List<User> findAll() {
		log.info("Finding all objects");
		List<User> findAll = userRepository.findAll();
		log.info("Found {} objects", findAll.size());
		return findAll;
	}
	//Metodo per salvataggio utente-ADMIN-SUPREMO	
		public User insertAdminSupremo(UserDto objectToInsert) {
			//verifico se già esiste l'username
			if(userRepository.existsByUsername(objectToInsert.getUsername())) {
				throw new EntityExistsException("Username already in use");
			}
			else {
			log.info("Inserting object: {}", objectToInsert);
			// Applico la codifica della password -- equivalente nella seguente riga
			// objectToInsert.setPassword(passwordEncoder.encode(objectToInsert.getPassword()));
			doBeforeSave(objectToInsert);
			//Costruisco un oggetto
			User result = new User();
			// copio le proprietà del dto nell'entity principale
			BeanUtils.copyProperties(objectToInsert, result);
			// aggiungo il ruolo
			result.addRole(developer);
			// Salvo l'utente che sarà ADMIN
			userRepository.save(result);
			log.info("Inserted object: {}", result);
			return result;
			}
		}
	//Metodo per salvataggio utente-ADMIN
	public User insertAdmin(UserDto objectToInsert) {
		//verifico se già esiste l'username
		if(userRepository.existsByUsername(objectToInsert.getUsername())) {
			throw new EntityExistsException("Username already in use");
		}
		else {
		log.info("Inserting object: {}", objectToInsert);
		// Applico la codifica della password -- equivalente nella seguente riga
		// objectToInsert.setPassword(passwordEncoder.encode(objectToInsert.getPassword()));
		doBeforeSave(objectToInsert);
		//Costruisco un oggetto
		User result = new User();
		// copio le proprietà del dto nell'entity principale
		BeanUtils.copyProperties(objectToInsert, result);
		// aggiungo il ruolo
		result.addRole(admin);
		// Salvo l'utente che sarà ADMIN
		userRepository.save(result);
		log.info("Inserted object: {}", result);
		return result;
		}
	}

	// Metodo per Salvataggio utente-USER
	public User insertUser(UserDto objectToInsert) {
		//verifico se già esiste l'username
		if(userRepository.existsByUsername(objectToInsert.getUsername())) {
			throw new EntityExistsException("Username already exist in use");
		}
		log.info("Inserting object: {}", objectToInsert);
		// Applico la codifica della password -- equivalente nella seguente riga
		// objectToInsert.setPassword(passwordEncoder.encode(objectToInsert.getPassword()));
		doBeforeSave(objectToInsert);
		User result = new User();
		// copio le proprietà del dto nell'entity principale
		BeanUtils.copyProperties(objectToInsert, result);
		// aggiungo il ruolo
		result.addRole(user);
		// Salvo l'utente che sarà USER
		userRepository.save(result);
		log.info("Inserted object: {}", result);
		return result;
	}

	void doBeforeSave(UserDto savedObject) {
		if (StringUtils.isNotEmpty(savedObject.getPassword())) {
			String encoded = passwordEncoder.encode(savedObject.getPassword());
			savedObject.setPassword(encoded);
		}
	}
	
	//Ottenere Utente tramite username - NO Case Sensitive
	public Optional<User> getByUsername(String name, boolean ignoreCase) {
		log.info("Finding object: name {} with ignoreCase {}", name, ignoreCase);
		Optional<User> resultOptional = Optional.empty();
		if (ignoreCase) {
			resultOptional = userRepository.findByUsernameIgnoreCase(name);
		} else {
			resultOptional = userRepository.findByUsernameIgnoreCase(name);
		}
		if (resultOptional.isPresent()) {
			log.info("Found object: name {}", name);
		} else {
			log.info("Not found object: name {}", name);
		}
		return resultOptional;
	}
	
	public User getById(Long id) {
		log.info("Finding object: id {}", id);
		User result= userRepository.findById(id).get();
		if (userRepository.existsById(id)) {
			log.info("Found object: id {}", id);
		} else {
			log.info("Not found object: id {}", id);
		}
		return result;
	}

	
	public void deleteById(Long id) {
//		if(!userRepository.existsById(id)) {
//			throw new EntityNotFoundException("User not found");
//		}
		userRepository.deleteById(id);
	}
	
	public User update(Long id, UserDto dto) {
		//verifico se esiste l'utente con id che passo
		if (!userRepository.existsById(id)) {
			throw new EntityNotFoundException("User Not Found");
		}
		//codifico la psw
		doBeforeSave(dto);
		//ottengo l'oggetto che voglio tramite id
		User user = getById(id);
		//copio le proprietà
		BeanUtils.copyProperties(dto, user);
		//,odifico le proprieta e salvo nel db
		return userRepository.save(user);
	}


	}


