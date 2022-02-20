package fr.orsys.gamesreviews.service;

import java.util.List;
import java.util.Optional;

import fr.orsys.gamesreviews.business.Genre;

public interface GenreService {

    Genre add(Genre genre);

    Genre getById(Long id);

	List<Genre> findAll();

	Optional<Genre> getByName(String name);

}
