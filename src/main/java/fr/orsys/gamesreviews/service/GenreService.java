package fr.orsys.gamesreviews.service;

import java.util.List;

import fr.orsys.gamesreviews.business.Genre;

public interface GenreService {

    Genre add(Genre genre);

    Genre getById(Long id);

	List<Genre> findAll();

}
