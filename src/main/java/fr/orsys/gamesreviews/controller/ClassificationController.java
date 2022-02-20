package fr.orsys.gamesreviews.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.orsys.gamesreviews.business.Classification;
import fr.orsys.gamesreviews.service.ClassificationService;
import lombok.AllArgsConstructor;

@AllArgsConstructor

@Validated
@RestController
@RequestMapping("/api/classification")
public class ClassificationController {
	  private final ClassificationService classificationService;
	 
	   @GetMapping
	   public ResponseEntity<List<Classification>> getClassifications() {
	        return new ResponseEntity<>(classificationService.getAllClassifications(), HttpStatus.OK);
	   }
	   
	   @GetMapping("/name")
	   public ResponseEntity<Optional<Classification>> getClassificationByName(@RequestParam String name) {
	        return new ResponseEntity<>(classificationService.findByName(name), HttpStatus.OK);
	   }
}
