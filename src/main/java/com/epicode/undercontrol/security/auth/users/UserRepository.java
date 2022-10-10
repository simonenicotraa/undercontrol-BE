package com.epicode.undercontrol.security.auth.users;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	public Optional<User> findByUsernameIgnoreCase(String username);
	Boolean existsByUsername(String username);
}
