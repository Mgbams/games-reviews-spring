package fr.orsys.gamesreviews.service.impl;

import fr.orsys.gamesreviews.business.Game;
import fr.orsys.gamesreviews.dto.GameDTO;
import fr.orsys.gamesreviews.exception.RecordAlreadyExistException;
import fr.orsys.gamesreviews.exception.RecordNotFoundException;
import fr.orsys.gamesreviews.mapper.Mapper;
import fr.orsys.gamesreviews.repository.GameRepository;
import fr.orsys.gamesreviews.service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor

@Service
public class GameServiceImpl implements GameService {

    private static final String GAME_NOT_FOUND = "Could not find game with id ";

    private final GameRepository gameRepository;
    private final Mapper<Game, GameDTO> mapper;

    @Override
    public long count() {
        return gameRepository.count();
    }

    @Override
    public GameDTO add(GameDTO gameDTO) {
        if (gameDTO == null) {
            throw new IllegalArgumentException("GameDTO must not be null");
        }

        if (gameRepository.findByName(gameDTO.getName()).isPresent()) {
            throw new RecordAlreadyExistException(
                    "Game with name \"" + gameDTO.getName() + "\" already exists");
        }

        Game game = gameRepository.save(mapper.mapDtoToEntity(gameDTO));
        return mapper.mapEntityToDto(game);
    }

    @Override
    public GameDTO getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Game id must not be null");
        }

        return gameRepository.findById(id)
                .map(mapper::mapEntityToDto)
                .orElseThrow(() -> new RecordNotFoundException(GAME_NOT_FOUND + id));
    }

    @Override
    public Page<GameDTO> getGames(Pageable pageable) {
        Page<Game> games = gameRepository.findAll(pageable);
        return games.map(mapper::mapEntityToDto);
    }

    @Override
    public GameDTO update(Long id, GameDTO gameDTO) {
        Optional<Game> dbRecord = gameRepository.findById(gameDTO.getId());
        if (dbRecord.isEmpty()) {
            throw new RecordNotFoundException(GAME_NOT_FOUND + gameDTO.getId());
        }

        Game changes = mapper.mapDtoToEntity(gameDTO);
        if (changes.equals(dbRecord.get())) {
            return gameDTO;
        }

        Game result = gameRepository.save(changes);
        return mapper.mapEntityToDto(result);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Game> game = gameRepository.findById(id);
        if (game.isEmpty()) {
            throw new RecordNotFoundException(GAME_NOT_FOUND + id);
        }
        gameRepository.deleteById(id);
    }

}
