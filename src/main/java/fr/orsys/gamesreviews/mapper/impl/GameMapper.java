package fr.orsys.gamesreviews.mapper.impl;

import fr.orsys.gamesreviews.business.Game;
import fr.orsys.gamesreviews.business.Platform;
import fr.orsys.gamesreviews.dto.GameDTO;
import fr.orsys.gamesreviews.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static fr.orsys.gamesreviews.util.DateTimeConverter.convertToDateViaInstant;
import static fr.orsys.gamesreviews.util.DateTimeConverter.convertToLocalDate;

@Component
public class GameMapper implements Mapper<Game, GameDTO> {

    @Override
    public Game mapDtoToEntity(GameDTO dto) {
        Game game = new Game();
        game.setId(dto.getId());
        game.setName(dto.getName());
        game.setDescription(dto.getDescription());
        game.setReleaseDate(convertToLocalDate(dto.getReleaseDate()));
        game.setPicture(dto.getPicture());
        game.setClassification(dto.getClassification());
        game.setGenre(dto.getGenre());
        game.setPublisher(dto.getPublisher());
        game.setBusinessModel(dto.getBusinessModel());
        List<Platform> platforms = new ArrayList<>(dto.getPlatforms());
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
