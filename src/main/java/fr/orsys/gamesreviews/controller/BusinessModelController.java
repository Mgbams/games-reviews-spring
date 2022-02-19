package fr.orsys.gamesreviews.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.orsys.gamesreviews.business.BusinessModel;
import fr.orsys.gamesreviews.service.BusinessModelService;
import lombok.AllArgsConstructor;

@AllArgsConstructor

@Validated
@RestController
@RequestMapping("/api/business-model")
public class BusinessModelController {

	private final BusinessModelService businessModelService;

    @GetMapping("")
    public ResponseEntity<List<BusinessModel>> getBusinessModels() {
        return new ResponseEntity<>(businessModelService.findAll(), HttpStatus.OK);
    }

}