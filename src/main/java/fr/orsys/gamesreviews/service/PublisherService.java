package fr.orsys.gamesreviews.service;

import fr.orsys.gamesreviews.business.Publisher;

public interface PublisherService {

    Publisher addPublisher(Publisher publisher);

    Publisher getPublisherByName(String name);

}
