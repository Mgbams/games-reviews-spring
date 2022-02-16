package fr.orsys.gamesreviews.service;

import fr.orsys.gamesreviews.business.BusinessModel;

public interface BusinessModelService {

    BusinessModel add(BusinessModel businessModel);

    BusinessModel getById(Long id);

}
