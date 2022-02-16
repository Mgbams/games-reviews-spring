package fr.orsys.gamesreviews.service;

import fr.orsys.gamesreviews.dto.GameDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GameService {

    long count();

    GameDTO add(GameDTO gameDTO);

    GameDTO getById(Long id);

    Page<GameDTO> getGames(Pageable pageable);

    GameDTO update(Long id, GameDTO gameDTO);

    void deleteById(Long id);

}
