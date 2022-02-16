package fr.orsys.gamesreviews.service;

import fr.orsys.gamesreviews.business.Publisher;

public interface PublisherService {

    Publisher add(Publisher publisher);

    Publisher getById(Long id);

}
