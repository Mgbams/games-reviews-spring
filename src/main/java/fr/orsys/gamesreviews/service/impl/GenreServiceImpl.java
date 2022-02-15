package fr.orsys.gamesreviews.service.impl;

import fr.orsys.gamesreviews.business.Genre;
import fr.orsys.gamesreviews.exception.RecordAlreadyExistException;
import fr.orsys.gamesreviews.exception.RecordNotFoundException;
import fr.orsys.gamesreviews.repository.GenreRepository;
import fr.orsys.gamesreviews.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public Genre addGenre(Genre genre) {
        if (genreRepository.getByName(genre.getName()).isPresent()) {
            throw new RecordAlreadyExistException("Genre with name \"" + genre.getName() + "\" already exists");
        }
        return genreRepository.save(genre);
    }

    @Override
    public Genre getGenreByName(String name) {
        return genreRepository.getByName(name)
                .orElseThrow(() ->new RecordNotFoundException("Could not find genre named " + name));
    }

}
