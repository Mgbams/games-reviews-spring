package fr.orsys.gamesreviews.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fr.orsys.gamesreviews.dto.GameDTO;
import fr.orsys.gamesreviews.service.GameService;
import lombok.AllArgsConstructor;

@AllArgsConstructor

@Validated
@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;
    private final String DIR_TO_UPLOAD = "src/main/resources/static/images/";

    @GetMapping("")
    public ResponseEntity<Page<GameDTO>> getGames(
            @PageableDefault @SortDefault(direction = Sort.Direction.DESC, sort = "releaseDate") Pageable pageable) {
        return new ResponseEntity<>(gameService.getGames(pageable), HttpStatus.OK);
    }
    
    @GetMapping("/pageable")
    public ResponseEntity<Page<GameDTO>> getPageableGames(@RequestParam() int page, @RequestParam() int size) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(gameService.getGames(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDTO> getGameById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(gameService.getById(id), HttpStatus.OK);
    }

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameDTO> addGame(@Valid @RequestBody GameDTO gameDTO) {
    	GameDTO newGame = new GameDTO();
    	newGame.setBusinessModel(gameDTO.getBusinessModel());
    	newGame.setClassification(gameDTO.getClassification());
    	newGame.setDescription(gameDTO.getDescription());
    	newGame.setGenre(gameDTO.getGenre());
    	newGame.setName(gameDTO.getName());
    	newGame.setPicture(gameDTO.getPicture().replace("C:\\fakepath\\", ""));
    	newGame.setPlatforms(gameDTO.getPlatforms());
    	newGame.setPublisher(gameDTO.getPublisher());
    	newGame.setReleaseDate(gameDTO.getReleaseDate());
    	
        return new ResponseEntity<>(gameService.add(newGame), HttpStatus.CREATED);
    }
    
    @PostMapping(path = "/multipart", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GameDTO> addGame(@RequestParam("fileName") MultipartFile file) throws IOException {
    	
    	Path chemin = Paths.get(DIR_TO_UPLOAD);
    	if (!Files.exists(chemin)) {
            Files.createDirectories(chemin);
        }

        try (InputStream inputStream = file.getInputStream()) {
            Path cheminFichier = chemin.resolve(file.getOriginalFilename().replace("C:\\fakepath\\", ""));
            Files.copy(inputStream, cheminFichier, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Erreur d'écriture : " + file.getOriginalFilename().replace("C:\\fakepath\\", ""), ioe);
        }
        
    	return null;
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
