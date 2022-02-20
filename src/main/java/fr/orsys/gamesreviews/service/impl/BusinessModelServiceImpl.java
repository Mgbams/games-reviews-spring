package fr.orsys.gamesreviews.service.impl;

import fr.orsys.gamesreviews.business.BusinessModel;
import fr.orsys.gamesreviews.exception.RecordAlreadyExistException;
import fr.orsys.gamesreviews.exception.RecordNotFoundException;
import fr.orsys.gamesreviews.repository.BusinessModelRepository;
import fr.orsys.gamesreviews.service.BusinessModelService;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@AllArgsConstructor

@Service
public class BusinessModelServiceImpl implements BusinessModelService {

    private final BusinessModelRepository businessModelRepository;

    @Override
    public BusinessModel add(BusinessModel businessModel) {
        if (businessModel == null) {
            throw new IllegalArgumentException("BusinessModel must not be null");
        }

        if (businessModelRepository.getByName(businessModel.getName()).isPresent()) {
            throw new RecordAlreadyExistException("BusinessModel with name \"" + businessModel.getName() + "\" already exists");
        }
        return businessModelRepository.save(businessModel);
    }

    @Override
    public BusinessModel getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("BusinessModel id must not be null");
        }
        return businessModelRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Could not find business model with id  " + id));
    }

	@Override
	public List<BusinessModel> findAll() {
		return businessModelRepository.findAll();
	}

	@Override
	public Optional<BusinessModel> getByName(String name) {
		if (businessModelRepository.getByName(name).isPresent()) {
			throw new RecordAlreadyExistException("Classification with name \"" + name + "\" already exists");
		}
		return businessModelRepository.getByName(name);
	}

}
