package fr.orsys.gamesreviews.service;

import java.util.List;
import java.util.Optional;

import fr.orsys.gamesreviews.business.BusinessModel;

public interface BusinessModelService {

    BusinessModel add(BusinessModel businessModel);

    BusinessModel getById(Long id);

	List<BusinessModel> findAll();

	Optional<BusinessModel> getByName(String name);

}
