package fr.orsys.gamesreviews.service.impl;

import fr.orsys.gamesreviews.business.BusinessModel;
import fr.orsys.gamesreviews.exception.RecordAlreadyExistException;
import fr.orsys.gamesreviews.exception.RecordNotFoundException;
import fr.orsys.gamesreviews.repository.BusinessModelRepository;
import fr.orsys.gamesreviews.service.BusinessModelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor

@Service
public class BusinessModelServiceImpl implements BusinessModelService {

    private final BusinessModelRepository businessModelRepository;

    @Override
    public BusinessModel addBusinessModel(BusinessModel businessModel) {
        if (businessModelRepository.getByName(businessModel.getName()).isPresent()) {
            throw new RecordAlreadyExistException("BusinessModel with name \"" + businessModel.getName() + "\" already exists");
        }
        return businessModelRepository.save(businessModel);
    }

    @Override
    public BusinessModel getBusinessModelByName(String name) {
        return businessModelRepository.getByName(name)
                .orElseThrow(() -> new RecordNotFoundException("Could not find business model named " + name));
    }

}