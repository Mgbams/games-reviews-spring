package fr.orsys.gamesreviews.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.orsys.gamesreviews.business.Publisher;
import fr.orsys.gamesreviews.service.PublisherService;
import lombok.AllArgsConstructor;

@AllArgsConstructor

@Validated
@RestController
@RequestMapping("/api/publisher")
public class PublisherController {

	private final PublisherService publisherService;

    @GetMapping("")
    public ResponseEntity<List<Publisher>> getPublishers() {
        return new ResponseEntity<>( publisherService.findAll(), HttpStatus.OK);
    }

}
