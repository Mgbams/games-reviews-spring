package fr.orsys.gamesreviews.service;

import fr.orsys.gamesreviews.business.Genre;

public interface GenreService {

    Genre addGenre(Genre genre);

    Genre getGenreByName(String name);

    Genre getById(Long id);

}
