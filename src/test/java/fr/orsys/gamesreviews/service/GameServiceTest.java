package fr.orsys.gamesreviews.service;

import fr.orsys.gamesreviews.business.Game;
import fr.orsys.gamesreviews.dto.GameDTO;
import fr.orsys.gamesreviews.exception.RecordAlreadyExistException;
import fr.orsys.gamesreviews.exception.RecordNotFoundException;
import fr.orsys.gamesreviews.mapper.Mapper;
import fr.orsys.gamesreviews.repository.GameRepository;
import fr.orsys.gamesreviews.service.impl.GameServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class GameServiceTest {

    @InjectMocks
    private GameServiceImpl serviceUnderTest;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private Mapper<Game, GameDTO> mapper;

    @Test
    void should_Count_Games_when_count() {
        // Arrange
        long expected = 5;
        when(gameRepository.count()).thenReturn(expected);

        // Act
        long result = serviceUnderTest.count();

        // Assert
        verify(gameRepository).count();
        assertEquals(expected, result);
    }

    @Test
    void should_Throw_IllegalArgumentException_When_add_And_GameDTO_null() {
        assertThrows(IllegalArgumentException.class, () -> serviceUnderTest.add(null));
    }

    @Test
    void should_Throw_RecordAlreadyExistException_When_add_And_Game_Exists() {
        String name = "Name";
        GameDTO gameDTO = new GameDTO();
        gameDTO.setName(name);

        Game game = new Game();
        game.setName(name);

        when(gameRepository.findByName(name)).thenReturn(Optional.of(game));

        assertThrows(RecordAlreadyExistException.class, () -> serviceUnderTest.add(gameDTO));
    }

    @Test
    void should_Add_Game_When_add() {
        // Arrange
        String name = "Name";
        Game game = new Game();
        game.setName(name);

        GameDTO expected = new GameDTO();
        expected.setName(name);

        when(gameRepository.save(any(Game.class))).thenReturn(game);
        when(mapper.mapDtoToEntity(any(GameDTO.class))).thenReturn(game);
        when(mapper.mapEntityToDto(any(Game.class))).thenReturn(expected);

        // Act
        GameDTO result = serviceUnderTest.add(expected);

        // Assert
        verify(gameRepository).save(game);
        verify(mapper).mapEntityToDto(game);
        verify(mapper).mapDtoToEntity(expected);
        assertEquals(expected, result);
    }

    @Test
    void should_Throw_IllegalArgumentException_When_getById_And_Id_null() {
        assertThrows(IllegalArgumentException.class, () -> serviceUnderTest.getById(null));
    }

    @Test
    void should_Throw_RecordNotFoundException_When_getById_And_BusinessModel_NotFound() {
        Long id = 1L;
        when(gameRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> serviceUnderTest.getById(id));
    }

    @Test
    void should_Return_BusinessModel_When_getById() {
        // Arrange
        Long id = 1L;
        Game game = new Game();
        game.setId(id);

        GameDTO expected = new GameDTO();
        expected.setId(id);

        when(gameRepository.findById(any(Long.class))).thenReturn(Optional.of(game));
        when(mapper.mapEntityToDto(any(Game.class))).thenReturn(expected);

        // Act
        GameDTO result = serviceUnderTest.getById(id);

        // Assert
        verify(gameRepository).findById(id);
        verify(mapper).mapEntityToDto(game);
        assertEquals(expected, result);
    }

    @Test
    void should_Return_Page_with_GamesDTO_When_getGames() {
        // Arrange
        PageRequest request = PageRequest.of(0, 1, Sort.Direction.valueOf("DESC"), "releaseDate");

        long gameId = 1;
        Game game = new Game();
        game.setId(gameId);

        GameDTO gameDTO = new GameDTO();
        gameDTO.setId(gameId);

        Page<Game> gamesPage = new PageImpl<>(List.of(game));

        Page<GameDTO> expected = new PageImpl<>(List.of(gameDTO));

        when(gameRepository.findAll(request)).thenReturn(gamesPage);
        when(mapper.mapEntityToDto(game)).thenReturn(gameDTO);

        // Act
        Page<GameDTO> result = serviceUnderTest.getGames(request);

        // Assert
        verify(gameRepository).findAll(request);
        verify(mapper).mapEntityToDto(game);
        assertEquals(expected, result);
    }

    @Test
    void should_Throw_RecordNotFoundException_When_update_And_Game_NotFound() {
        long id = 1;
        GameDTO gameDTO = new GameDTO();

        when(gameRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> serviceUnderTest.update(id, gameDTO));
    }

    @Test
    void should_Return_UnmodifiableGameDTO_When_update_And_nothing_To_Update() {
        long id = 1;
        Game game = new Game();
        game.setId(id);

        GameDTO expected = new GameDTO();
        expected.setId(id);

        when(gameRepository.findById(id)).thenReturn(Optional.of(game));
        when(mapper.mapDtoToEntity(any(GameDTO.class))).thenReturn(game);

        GameDTO result = serviceUnderTest.update(id, expected);

        assertEquals(expected, result);
    }

    @Test
    void should_Update_Game_When_update() {
        // Arrange
        long id = 1;
        String oldName = "Old name";
        String newName = "New name";

        GameDTO expected = new GameDTO();
        expected.setId(id);
        expected.setName(newName);

        Game oldGame = new Game();
        oldGame.setId(id);
        oldGame.setName(oldName);

        Game gameToSave = new Game();
        gameToSave.setId(expected.getId());
        gameToSave.setName(expected.getName());

        when(gameRepository.findById(id)).thenReturn(Optional.of(oldGame));
        when(mapper.mapDtoToEntity(expected)).thenReturn(gameToSave);
        when(gameRepository.save(gameToSave)).thenReturn(gameToSave);
        when(mapper.mapEntityToDto(gameToSave)).thenReturn(expected);

        // Act
        GameDTO result = serviceUnderTest.update(id, expected);

        // Assert
        verify(gameRepository).save(gameToSave);
        assertEquals(expected.getId(), result.getId());
    }

    @Test
    void should_Throw_RecordNotFoundException_When_deleteById_And_Game_NotFound() {
        long id = 1;
        when(gameRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> serviceUnderTest.deleteById(id));
    }

    @Test
    void should_Delete_Game_When_deleteById() {
        // Arrange
        long id = 1;
        Game game = new Game();
        game.setId(id);

        when(gameRepository.findById(any(Long.class))).thenReturn(Optional.of(game));

        // Act
        serviceUnderTest.deleteById(id);

        // Assert
        verify(gameRepository, times(1)).deleteById(id);
    }

}
