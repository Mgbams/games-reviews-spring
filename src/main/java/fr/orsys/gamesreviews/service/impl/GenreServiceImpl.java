package fr.orsys.gamesreviews.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.orsys.gamesreviews.business.Genre;
import fr.orsys.gamesreviews.exception.RecordAlreadyExistException;
import fr.orsys.gamesreviews.exception.RecordNotFoundException;
import fr.orsys.gamesreviews.repository.GenreRepository;
import fr.orsys.gamesreviews.service.GenreService;
import lombok.AllArgsConstructor;

@AllArgsConstructor

@Service
public class GenreServiceImpl implements GenreService {

	private final GenreRepository genreRepository;

	@Override
	public Genre add(Genre genre) {
		if (genre == null) {
			throw new IllegalArgumentException("Genre must not be null");
		}

		if (genreRepository.getByName(genre.getName()).isPresent()) {
			throw new RecordAlreadyExistException("Genre with name \"" + genre.getName() + "\" already exists");
		}
		return genreRepository.save(genre);
	}

	@Override
	public Genre getById(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Genre id must not be null");
		}
		return genreRepository.findById(id)
				.orElseThrow(() -> new RecordNotFoundException("Could not find genre with id  " + id));
	}

	@Override
	public List<Genre> findAll() {

		return genreRepository.findAll();
	}

	@Override
	public Optional<Genre> getByName(String name) {

		if (genreRepository.getByName(name).isPresent()) {
			throw new RecordAlreadyExistException("Classification with name \"" + name + "\" already exists");
		}
		return genreRepository.getByName(name);

	}

}
