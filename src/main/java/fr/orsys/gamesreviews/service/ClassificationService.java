package fr.orsys.gamesreviews.service;

import java.util.List;
import java.util.Optional;

import fr.orsys.gamesreviews.business.Classification;

public interface ClassificationService {

	long countClassifications();

	Classification add(Classification classification);

	Classification getById(Long id);

	List<Classification> getAllClassifications();

	Optional<Classification> findByName(String name);

}
