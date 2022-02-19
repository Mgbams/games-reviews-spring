package fr.orsys.gamesreviews.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.orsys.gamesreviews.business.Genre;
import fr.orsys.gamesreviews.service.GenreService;
import lombok.AllArgsConstructor;

@AllArgsConstructor

@Validated
@RestController
@RequestMapping("/api/genre")
public class GenreController {
	  private final GenreService genreService;
	 
	   @GetMapping
	   public ResponseEntity<List<Genre>> getGenres() {
	        return new ResponseEntity<>(genreService.findAll(), HttpStatus.OK);
	   }
}

