package fr.orsys.gamesreviews.service.impl;

import fr.orsys.gamesreviews.business.Review;
import fr.orsys.gamesreviews.business.user.Moderator;
import fr.orsys.gamesreviews.dto.ReviewDTO;
import fr.orsys.gamesreviews.exception.RecordAlreadyExistException;
import fr.orsys.gamesreviews.exception.RecordNotFoundException;
import fr.orsys.gamesreviews.mapper.Mapper;
import fr.orsys.gamesreviews.repository.ReviewRepository;
import fr.orsys.gamesreviews.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final Mapper<Review, ReviewDTO> mapper;

    @Override
    public Page<ReviewDTO> getValidatedReviews(Pageable pageable) {
        Page<Review> reviews = reviewRepository.findAllByModerationDateTimeIsNotNull(pageable);
        return reviews.map(mapper::mapEntityToDto);
    }

    @Override
    public Page<ReviewDTO> getValidatedReviewsByGame(Long id, Pageable pageable) {
        if (id == null) {
            throw new IllegalArgumentException("Missing game ID");
        }
        Page<Review> reviews = reviewRepository.findAllByGameIdAndModerationDateTimeIsNotNull(id, pageable);
        return reviews.map(mapper::mapEntityToDto);
    }

    @Override
    public Page<ReviewDTO> getPendingReviews(Pageable pageable) {
        Page<Review> reviews = reviewRepository.findAllByModerationDateTimeIsNull(pageable);
        return reviews.map(mapper::mapEntityToDto);
    }

    @Override
    public ReviewDTO add(ReviewDTO reviewDTO) {
        if (reviewDTO == null) {
            throw new IllegalArgumentException("GameDTO must not be null");
        }

        Optional<Review> exist = reviewRepository.findByGameIdAndPlayerId(
                reviewDTO.getGame().id(),
                reviewDTO.getPlayer().id());
        if (exist.isPresent()) {
            throw new RecordAlreadyExistException(
                    "A review by player " + reviewDTO.getPlayer().id() +
                    " for game " + reviewDTO.getGame().id()+ " already exists");
        }

        reviewDTO.setPublicationDateTime(LocalDateTime.now());
        Review review = reviewRepository.save(mapper.mapDtoToEntity(reviewDTO));
        return mapper.mapEntityToDto(review);
    }

    @Override
    public ReviewDTO validate(Long reviewId, Long moderatorId) {
        Optional<Review> dbRecord = reviewRepository.findById(reviewId);
        if (dbRecord.isEmpty()) {
            throw new RecordNotFoundException("Could not find review with id " + reviewId);
        }
        Review review = dbRecord.get();
        Moderator moderator = new Moderator();
        moderator.setId(moderatorId);
        review.setModerator(moderator);
        review.setModerationDateTime(LocalDateTime.now());

        review = reviewRepository.save(review);
        return mapper.mapEntityToDto(review);
    }

    @Override
    public void delete(Long id) {
        Optional<Review> review = reviewRepository.findById(id);
        if (review.isEmpty()) {
            throw new RecordNotFoundException("Could not find review with id " + id);
        }
        reviewRepository.deleteById(id);
    }

}
