package fr.orsys.gamesreviews.service;

import fr.orsys.gamesreviews.business.Genre;

public interface GenreService {

    Genre add(Genre genre);

    Genre getById(Long id);

}
