package fr.orsys.gamesreviews.controller;

import fr.orsys.gamesreviews.dto.GameDTO;
import fr.orsys.gamesreviews.service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    @GetMapping("")
    public ResponseEntity<Page<GameDTO>> getGames(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "releaseDate") String sort,
            @RequestParam(value = "direction", defaultValue = "desc") String direction
    ) {
        return new ResponseEntity<>(gameService.getGames(page, size, sort, direction), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDTO> getGameById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(gameService.getGameById(id), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<GameDTO> addGame(@Valid @RequestBody GameDTO gameDTO) {
        return new ResponseEntity<>(gameService.addGame(gameDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameDTO> updateGame(@Valid @RequestBody GameDTO gameDTO,
                                              @PathVariable("id") Long id) {
        return new ResponseEntity<>(gameService.updateGame(id, gameDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGame(@PathVariable("id") Long id) {
        gameService.deleteGameById(id);
        return new ResponseEntity<>("Game deleted successfully", HttpStatus.NO_CONTENT);
    }

}
