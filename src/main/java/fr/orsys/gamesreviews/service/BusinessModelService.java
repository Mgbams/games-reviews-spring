package fr.orsys.gamesreviews.service;

import java.util.List;

import fr.orsys.gamesreviews.business.BusinessModel;

public interface BusinessModelService {

    BusinessModel add(BusinessModel businessModel);

    BusinessModel getById(Long id);

	List<BusinessModel> findAll();

}
