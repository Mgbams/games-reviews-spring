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
    void should_Add_Genre_When_add() {
        // Arrange
        Genre expected = new Genre();
        expected.setName("Genre");
        when(genreRepository.save(any(Genre.class))).thenReturn(expected);

        // Act
        Genre result = serviceUnderTest.add(expected);

        // Assert
        verify(genreRepository).save(expected);
        assertEquals(expected, result);
    }

    @Test
    void should_Throw_IllegalArgumentException_When_add_And_Genre_null() {
        assertThrows(IllegalArgumentException.class, () -> serviceUnderTest.add(null));
    }

    @Test
    void should_Throw_RecordAlreadyExistException_When_add_And_Genre_Exists() {
        String name = "Name";
        Genre genre = new Genre();
        genre.setName(name);
        when(genreRepository.getByName(name)).thenReturn(Optional.of(genre));

        assertThrows(RecordAlreadyExistException.class, () -> serviceUnderTest.add(genre));
    }

    @Test
    void should_Throw_IllegalArgumentException_When_getId_And_Id_null() {
        assertThrows(IllegalArgumentException.class, () -> serviceUnderTest.getById(null));
    }

    @Test
    void should_Throw_RecordNotFoundException_When_getById_And_Genre_NotFound() {
        Long id = 1L;
        when(genreRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> serviceUnderTest.getById(id));
    }

    @Test
    void should_Return_Genre_When_getById() {
        Long id = 1L;
        Genre expected = new Genre();
        expected.setId(id);
        when(genreRepository.findById(any(Long.class))).thenReturn(Optional.of(expected));

        Genre result = serviceUnderTest.getById(id);

        verify(genreRepository).findById(id);
        assertEquals(expected, result);
    }

}
