package fr.orsys.gamesreviews.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import fr.orsys.gamesreviews.business.BusinessModel;
import fr.orsys.gamesreviews.business.Classification;
import fr.orsys.gamesreviews.business.Genre;
import fr.orsys.gamesreviews.business.Platform;
import fr.orsys.gamesreviews.business.Publisher;
import lombok.Data;

@Data
public class GameDTO {
    
    Long id;

    @NotBlank(message = "{game.name.null}")
    @Size(max = 255, message = "{game.name.size}")
    private String name;

    @NotBlank(message = "{game.description.null}")
    private String description;

    @NotNull(message = "game.releaseDate.null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past(message = "game.releaseDate.past")
    private Date releaseDate;

    @NotBlank(message = "{game.picture.null}")
    @Size(max = 255, message = "{game.picture.size}")
    private String picture;

    @NotNull(message = "{game.genre.null}")
    private Genre genre;

    @NotNull(message = "{game.classification.null}")
    private Classification classification;

    @NotNull(message = "{game.publisher.null}")
    private Publisher publisher;

    @NotNull(message = "{game.businessModel.null}")
    private BusinessModel businessModel;

    @NotEmpty(message = "{game.platforms.null}")
    private List<Platform> platforms;

}
