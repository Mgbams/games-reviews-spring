package fr.orsys.gamesreviews.bootstrap;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.orsys.gamesreviews.business.BusinessModel;
import fr.orsys.gamesreviews.business.Classification;
import fr.orsys.gamesreviews.business.Genre;
import fr.orsys.gamesreviews.business.Platform;
import fr.orsys.gamesreviews.business.Publisher;
import fr.orsys.gamesreviews.dto.GameDTO;
import fr.orsys.gamesreviews.exception.RecordAlreadyExistException;
import fr.orsys.gamesreviews.service.BusinessModelService;
import fr.orsys.gamesreviews.service.ClassificationService;
import fr.orsys.gamesreviews.service.GameService;
import fr.orsys.gamesreviews.service.GenreService;
import fr.orsys.gamesreviews.service.PlatformService;
import fr.orsys.gamesreviews.service.PublisherService;
import fr.orsys.gamesreviews.service.ReviewService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor

@Component
public class BootStrapData implements CommandLineRunner {

    private final GameService gameService;
    private final ClassificationService classificationService;
    private final PlatformService platformService;
    private final GenreService genreService;
    private final PublisherService publisherService;
    private final BusinessModelService businessModelService;
    private final ReviewService reviewService;

    @Override
    public void run(String... args) {
        loadPlatforms();
        loadClassifications();
        loadGames();
    }

    private void loadPlatforms() {
        if (platformService.countPlatforms() == 0) {
            String[] platforms = {"Windows", "macOS", "Linux", "Android", "iOS",
                    "Playstation 3", "Playstation 4", "Xbox ONE", "Xbox 360"};
            for (String name : platforms) {
                Platform platform = new Platform();
                platform.setName(name);
                platformService.add(platform);
            }
        }
    }
    
    private void loadClassifications() {
        if (classificationService.countClassifications() == 0) {
            int[] ages = {3, 7, 12, 16, 18};
            for (int age : ages) {
                Classification classification = new Classification();
                classification.setName("PEGI " + age);
                classificationService.add(classification);
            }
        }
    }

    private void loadGames() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<GameDTO>> typeReference = new TypeReference<>() {};

        try {
            File resource = new ClassPathResource("games.json").getFile();
            List<GameDTO> games = mapper.readValue(resource, typeReference);
            if (gameService.count() == 0) {
                games.forEach(this::insertGame);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void insertGame(GameDTO dto) {
        addGenreIfNotExist(dto.getGenre());
        addPublisherIfNotExist(dto.getPublisher());
        addBusinessModelIfNotExist(dto.getBusinessModel());

        dto = gameService.add(dto);
        log.debug("Added Game : \"{}\"", dto.getName());
    }

    private void addGenreIfNotExist(Genre genre) {
        try {
            genre = genreService.add(genre);
            log.info("Added Genre : \"{}\"", genre.getName());
        } catch (RecordAlreadyExistException e) {
            log.warn(e.getMessage());
        }
    }

    private void addPublisherIfNotExist(Publisher publisher) {
        try {
            publisher = publisherService.add(publisher);
            log.info("Added Publisher : \"{}\"", publisher.getName());
        } catch (RecordAlreadyExistException e) {
            log.warn(e.getMessage());
        }
    }

    private void addBusinessModelIfNotExist(BusinessModel businessModel) {
        try {
            businessModel = businessModelService.add(businessModel);
            log.info("Added Business Model : \"{}\"", businessModel.getName());
        } catch (RecordAlreadyExistException e) {
            log.warn(e.getMessage());
        }
    }

}
