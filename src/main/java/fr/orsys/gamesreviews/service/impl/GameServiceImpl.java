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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor

@Service
public class GameServiceImpl implements GameService {

    private static final String GAME_NOT_FOUND = "Could not find game with id ";

    private final GameRepository gameRepository;
    private final Mapper<Game, GameDTO> mapper;

    @Override
    public long countGames() {
        return gameRepository.count();
    }

    @Override
    public GameDTO addGame(GameDTO gameDTO) {
        if (gameRepository.findByName(gameDTO.getName()).isPresent()) {
            throw new RecordAlreadyExistException(
                    "Game with name \"" + gameDTO.getName() + "\" already exists");
        }
        Game game = gameRepository.save(mapper.mapDtoToEntity(gameDTO));
        return mapper.mapEntityToDto(game);
    }

    @Override
    public GameDTO getGameById(Long id) {
        return gameRepository.findById(id)
                .map(mapper::mapEntityToDto)
                .orElseThrow(() -> new RecordNotFoundException(GAME_NOT_FOUND + id));
    }

    @Override
    public Page<GameDTO> getGames(int page, int size, String sort, String direction) {
        Page<Game> games = gameRepository.findAll(
                PageRequest.of(page - 1, size, Sort.Direction.fromString(direction), sort));

        if (!games.hasContent()) {
            throw new RecordNotFoundException("Page " + page + " doesn't exist");
        }

        return games.map(mapper::mapEntityToDto);
    }

    @Override
    public GameDTO updateGame(Long id, GameDTO gameDTO) {
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
    public void deleteGameById(Long id) {
        Optional<Game> game = gameRepository.findById(id);
        if (game.isEmpty()) {
            throw new RecordNotFoundException(GAME_NOT_FOUND + id);
        }
        gameRepository.deleteById(id);
    }

}
