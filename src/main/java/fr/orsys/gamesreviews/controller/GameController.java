package fr.orsys.gamesreviews.controller;

import fr.orsys.gamesreviews.dto.GameDTO;
import fr.orsys.gamesreviews.service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor

@Validated
@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    @GetMapping("")
    public ResponseEntity<Page<GameDTO>> getGames(
            @PageableDefault @SortDefault(direction = Sort.Direction.DESC, sort = "releaseDate") Pageable pageable) {
        return new ResponseEntity<>(gameService.getGames(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDTO> getGameById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(gameService.getById(id), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<GameDTO> addGame(@Valid @RequestBody GameDTO gameDTO) {
        return new ResponseEntity<>(gameService.add(gameDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameDTO> updateGame(@Valid @RequestBody GameDTO gameDTO,
                                              @PathVariable("id") Long id) {
        return new ResponseEntity<>(gameService.update(id, gameDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGame(@PathVariable("id") Long id) {
        gameService.deleteById(id);
        return new ResponseEntity<>("Game deleted successfully", HttpStatus.NO_CONTENT);
    }

}
