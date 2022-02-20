package fr.orsys.gamesreviews.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.orsys.gamesreviews.business.Classification;
import fr.orsys.gamesreviews.exception.RecordAlreadyExistException;
import fr.orsys.gamesreviews.exception.RecordNotFoundException;
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
	public List<Classification> getAllClassifications() {
		return classificationRepository.findAll();
	}

	@Override
	public Classification add(Classification classification) {
		if (classification == null) {
			throw new IllegalArgumentException("Classification must not be null");
		}

		if (classificationRepository.getByName(classification.getName()).isPresent()) {
			throw new RecordAlreadyExistException(
					"Classification with name \"" + classification.getName() + "\" already exists");
		}
		return classificationRepository.save(classification);
	}

	@Override
	public Classification getById(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Classification id must not be null");
		}
		return classificationRepository.findById(id)
				.orElseThrow(() -> new RecordNotFoundException("Could not find Classification with id " + id));
	}

	@Override
	public Optional<Classification> findByName(String name) {

		if (classificationRepository.findByName(name).isPresent()) {
			throw new RecordAlreadyExistException(
					"Classification with name \"" + name + "\" already exists");
		}
		return classificationRepository.findByName(name);
	}

}
