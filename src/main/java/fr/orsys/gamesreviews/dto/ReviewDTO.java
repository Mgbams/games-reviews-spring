package fr.orsys.gamesreviews.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
public class ReviewDTO {

    private Long id;

    @NotNull(message = "{review.score.null}")
    @Range(max = 20, message = "{review.score.range}")
    private Integer score;

    @NotBlank(message = "{review.description.null}")
    private String description;

    @NotNull(message = "{review.game.null}")
    private Game game;

    @NotNull(message = "{review.player.null}")
    private User player;

    @NotNull(message = "{review.publicationDateTime.null}")
    @Past(message = "{review.publicationDateTime.past}")
    private LocalDateTime publicationDateTime;

    private User moderator;
    private LocalDateTime moderationDateTime;

    private boolean validated;

    public record Game(Long id, String name) { }
    public record User(Long id, String pseudonym) { }

}
