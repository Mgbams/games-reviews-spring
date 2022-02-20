package fr.orsys.gamesreviews.mapper.impl;

import fr.orsys.gamesreviews.business.Game;
import fr.orsys.gamesreviews.business.Review;
import fr.orsys.gamesreviews.business.user.Moderator;
import fr.orsys.gamesreviews.business.user.Player;
import fr.orsys.gamesreviews.dto.ReviewDTO;
import fr.orsys.gamesreviews.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper implements Mapper<Review, ReviewDTO> {

    @Override
    public Review mapDtoToEntity(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setId(reviewDTO.getId());
        review.setDescription(reviewDTO.getDescription());
        review.setPublicationDateTime(reviewDTO.getPublicationDateTime());
        review.setModerationDateTime(reviewDTO.getModerationDateTime());
        review.setScore(reviewDTO.getScore());

        if (reviewDTO.getGame() != null) {
            Game game = new Game();
            game.setId(reviewDTO.getGame().id());
            review.setGame(game);
        }

        if (reviewDTO.getPlayer() != null) {
            Player player = new Player();
            player.setId(reviewDTO.getPlayer().id());
            review.setPlayer(player);
        }

        if (reviewDTO.getModerator() != null) {
            Moderator moderator = new Moderator();
            moderator.setId(reviewDTO.getModerator().id());
            review.setModerator(moderator);
        }
        return review;
    }

    @Override
    public ReviewDTO mapEntityToDto(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setScore(review.getScore());
        dto.setDescription(review.getDescription());

        if (review.getGame() != null) {
            dto.setGame(new ReviewDTO.Game(
                    review.getGame().getId(),
                    review.getGame().getName()
            ));
        }

        if (review.getPlayer() != null) {
            dto.setPlayer(new ReviewDTO.User(
                    review.getPlayer().getId(),
                    review.getPlayer().getPseudonym()
            ));
        }
        dto.setPublicationDateTime(review.getPublicationDateTime());

        if (review.getModerator() != null) {
            dto.setModerator(new ReviewDTO.User(
                    review.getModerator().getId(),
                    review.getModerator().getPseudonym()
            ));
            dto.setModerationDateTime(review.getModerationDateTime());
            dto.setValidated(true);
        }
        return dto;
    }

}
