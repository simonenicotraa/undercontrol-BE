package com.epicode.undercontrol.image;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ImageDataRepository extends JpaRepository<ImageData,Long> {
	 Optional<ImageData> findByName(String fileName);
}
