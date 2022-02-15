package fr.orsys.gamesreviews.service;

import fr.orsys.gamesreviews.business.BusinessModel;

public interface BusinessModelService {

    BusinessModel addBusinessModel(BusinessModel businessModel);

    BusinessModel getBusinessModelByName(String name);

}
