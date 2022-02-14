package fr.orsys.gamesreviews.business;

import fr.orsys.gamesreviews.business.user.Moderator;
import fr.orsys.gamesreviews.business.user.Player;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

@Getter
@Setter

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private LocalDateTime publicationDateTime;
    private LocalDateTime moderationDateTime;
    private Float score;

    @ManyToOne
    private Game game;

    @ManyToOne
    private Player player;

    @ManyToOne
    private Moderator moderator;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(id, review.id) &&
                Objects.equals(description, review.description) &&
                Objects.equals(publicationDateTime, review.publicationDateTime) &&
                Objects.equals(moderationDateTime, review.moderationDateTime) &&
                Objects.equals(score, review.score) &&
                Objects.equals(game, review.game) &&
                Objects.equals(player, review.player) &&
                Objects.equals(moderator, review.moderator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, publicationDateTime, moderationDateTime, score, game, player, moderator);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Review.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("description='" + description + "'")
                .add("publicationDateTime=" + publicationDateTime)
                .add("moderationDateTime=" + moderationDateTime)
                .add("score=" + score)
                .add("game=" + game.getName())
                .add("player=" + player.getPseudonym())
                .add("moderator=" + moderator.getPseudonym())
                .toString();
    }

}

