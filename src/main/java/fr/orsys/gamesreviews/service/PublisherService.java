package fr.orsys.gamesreviews.service;

import java.util.List;
import java.util.Optional;

import fr.orsys.gamesreviews.business.Publisher;

public interface PublisherService {

    Publisher add(Publisher publisher);

    Publisher getById(Long id);

	List<Publisher> findAll();

	Optional<Publisher> getByName(String name);

}
