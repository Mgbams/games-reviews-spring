package fr.orsys.gamesreviews.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.orsys.gamesreviews.business.Platform;
import fr.orsys.gamesreviews.service.PlatformService;
import lombok.AllArgsConstructor;

@AllArgsConstructor

@Validated
@RestController
@RequestMapping("/api/platform")
public class PlatformController {
	
	private final PlatformService platformService;

	 @GetMapping("")
	    public ResponseEntity<List<Platform>> getPlatforms() {
	        return new ResponseEntity<>(platformService.findAll(), HttpStatus.OK);
	    }
}
