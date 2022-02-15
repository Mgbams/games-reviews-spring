package fr.orsys.gamesreviews.service;

import fr.orsys.gamesreviews.dto.GameDTO;
import org.springframework.data.domain.Page;

public interface GameService {

    long countGames();

    GameDTO addGame(GameDTO gameDTO);

    GameDTO getGameById(Long id);

    Page<GameDTO> getGames(int page, int size, String sort, String direction);

    GameDTO updateGame(Long id, GameDTO gameDTO);

    void deleteGameById(Long id);

}
