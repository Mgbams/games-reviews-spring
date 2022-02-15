package fr.orsys.gamesreviews.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.orsys.gamesreviews.business.Classification;

public interface ClassificationRepository extends JpaRepository<Classification, Long> {

	Optional<Object> getByName(String name);

}
