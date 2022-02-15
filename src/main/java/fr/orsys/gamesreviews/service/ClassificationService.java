package fr.orsys.gamesreviews.service;

import fr.orsys.gamesreviews.business.Classification;

public interface ClassificationService {

	long countClassifications();

	Classification addClassification(Classification classification);

	Classification getById(Long id);

}
