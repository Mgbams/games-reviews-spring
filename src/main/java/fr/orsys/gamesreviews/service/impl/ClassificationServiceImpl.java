package fr.orsys.gamesreviews.service.impl;

import org.springframework.stereotype.Service;

import fr.orsys.gamesreviews.business.Classification;
import fr.orsys.gamesreviews.exception.RecordAlreadyExistException;
import fr.orsys.gamesreviews.repository.ClassificationRepository;
import fr.orsys.gamesreviews.service.ClassificationService;
import lombok.AllArgsConstructor;

@AllArgsConstructor

@Service
public class ClassificationServiceImpl implements ClassificationService {

	private final ClassificationRepository classificationRepository;

	@Override
	public long countClassifications() {
		return classificationRepository.count();
	}

	@Override
	public Classification addClassification(Classification classification) {
		if (classificationRepository.getByName(classification.getName()).isPresent()) {
			throw new RecordAlreadyExistException(
					"Classification with name \"" + classification.getName() + "\" already exists");
		}
		return classificationRepository.save(classification);
	}

}
