package fr.orsys.gamesreviews.mapper.impl;

import fr.orsys.gamesreviews.business.Game;
import fr.orsys.gamesreviews.business.Platform;
import fr.orsys.gamesreviews.dto.GameDTO;
import fr.orsys.gamesreviews.mapper.Mapper;
import fr.orsys.gamesreviews.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static fr.orsys.gamesreviews.util.DateTimeConverter.convertToDateViaInstant;
import static fr.orsys.gamesreviews.util.DateTimeConverter.convertToLocalDate;

@AllArgsConstructor

@Component
public class GameMapper implements Mapper<Game, GameDTO> {

    private final ClassificationService classificationService;
    private final GenreService genreService;
    private final PublisherService publisherService;
    private final BusinessModelService businessModelService;
    private final PlatformService platformService;

    @Override
    public Game mapDtoToEntity(GameDTO dto) {
        Game game = new Game();
        game.setId(dto.getId());
        game.setName(dto.getName());
        game.setDescription(dto.getDescription());
        game.setReleaseDate(convertToLocalDate(dto.getReleaseDate()));
        game.setPicture(dto.getPicture());

        game.setClassification(dto.getClassification().getId() == null
                ? dto.getClassification()
                : classificationService.getById(dto.getClassification().getId())
        );

        game.setGenre(dto.getGenre().getId() == null
                ? dto.getGenre()
                : genreService.getById(dto.getGenre().getId())
        );

        game.setPublisher(dto.getPublisher().getId() == null
                ? dto.getPublisher()
                : publisherService.getById(dto.getPublisher().getId())
        );

        game.setBusinessModel(dto.getBusinessModel().getId() == null
                ? dto.getBusinessModel()
                : businessModelService.getById(dto.getBusinessModel().getId())
        );

        List<Platform> platforms = new ArrayList<>();
        for (Platform platform : dto.getPlatforms()) {
            if (platform.getId() == null) {
                platforms.add(platform);
            } else {
                platforms.add(platformService.getById(platform.getId()));
            }
        }
        game.setPlatforms(platforms);
        return game;
    }

    @Override
    public GameDTO mapEntityToDto(Game game) {
        GameDTO dto = new GameDTO();
        dto.setId(game.getId());
        dto.setName(game.getName());
        dto.setDescription(game.getDescription());
        dto.setReleaseDate(convertToDateViaInstant(game.getReleaseDate()));
        dto.setPicture(game.getPicture());
        dto.setClassification(game.getClassification());
        dto.setGenre(game.getGenre());
        dto.setPublisher(game.getPublisher());
        dto.setBusinessModel(game.getBusinessModel());
        dto.setPlatforms(game.getPlatforms());
        return dto;
    }

}
