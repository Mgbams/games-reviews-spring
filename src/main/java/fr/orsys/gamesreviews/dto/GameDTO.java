package fr.orsys.gamesreviews.dto;

import fr.orsys.gamesreviews.business.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

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
