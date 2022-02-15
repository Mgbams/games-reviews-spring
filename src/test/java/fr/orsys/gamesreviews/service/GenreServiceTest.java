package fr.orsys.gamesreviews.service;

import fr.orsys.gamesreviews.business.Genre;
import fr.orsys.gamesreviews.exception.RecordAlreadyExistException;
import fr.orsys.gamesreviews.exception.RecordNotFoundException;
import fr.orsys.gamesreviews.repository.GenreRepository;
import fr.orsys.gamesreviews.service.impl.GenreServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class GenreServiceTest {

    @InjectMocks
    private GenreServiceImpl serviceUnderTest;

    @Mock
    private GenreRepository genreRepository;

    @Test
    void should_add_genre() {
        // Arrange
        Genre expected = new Genre();
        expected.setName("Genre");

        when(genreRepository.save(any(Genre.class))).thenReturn(expected);

        // Act
        Genre result = serviceUnderTest.addGenre(expected);

        // Assert
        verify(genreRepository).save(expected);
        assertEquals(expected, result);
    }

    @Test
    void should_throw_RecordAlreadyExistException_when_genre_exists() {
        String name = "Name";
        Genre genre = new Genre();
        genre.setName(name);
        when(genreRepository.getByName(name)).thenReturn(Optional.of(genre));

        assertThrows(RecordAlreadyExistException.class, () -> serviceUnderTest.addGenre(genre));
    }

    @Test
    void should_return_genre_by_name() {
        String name = "Name";
        Genre expected = new Genre();
        expected.setName(name);
        when(genreRepository.getByName(any(String.class))).thenReturn(Optional.of(expected));

        Genre result = serviceUnderTest.getGenreByName(name);

        verify(genreRepository).getByName(name);
        assertEquals(expected, result);
    }

    @Test
    void should_throw_RecordNotFoundException_when_genre_not_found() {
        String name = "Name";
        Genre genre = new Genre();
        genre.setName(name);
        when(genreRepository.getByName(name)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> serviceUnderTest.getGenreByName(name));
    }
}
