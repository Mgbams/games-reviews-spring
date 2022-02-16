package fr.orsys.gamesreviews.service;

import fr.orsys.gamesreviews.dto.GameDTO;
import org.springframework.data.domain.Page;

public interface GameService {

    long count();

    GameDTO add(GameDTO gameDTO);

    GameDTO getById(Long id);

    Page<GameDTO> getGames(int page, int size, String direction, String sort);

    GameDTO update(Long id, GameDTO gameDTO);

    void deleteById(Long id);

}
