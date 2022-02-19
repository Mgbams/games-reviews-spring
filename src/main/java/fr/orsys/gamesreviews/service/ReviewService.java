package fr.orsys.gamesreviews.service;

import fr.orsys.gamesreviews.dto.ReviewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Abstract interface for Review Service
 *
 * @author Oleg NOVAK
 */
public interface ReviewService {

    /**
     * This method returns a page with validated reviews from the database,
     * parameterized by the Pageable parameter
     *
     * @param pageable  the {@link Pageable} contains pagination information
     * @return          the {@link Page} with {@link ReviewDTO} records
     */
    Page<ReviewDTO> getValidatedReviews(Pageable pageable);


    /**
     * This method returns a page with validated reviews for the specified game
     * from the database, parameterized by the Pageable parameter
     *
     * @param id        the {@link Long} game ID
     * @param pageable  the {@link Pageable} contains pagination information
     * @return          the {@link Page} with {@link ReviewDTO} records
     */
    Page<ReviewDTO> getValidatedReviewsByGame(Long id, Pageable pageable);

    /**
     * This method returns a page with pending reviews from the database,
     * parameterized by the Pageable parameter
     *
     * @param pageable  the {@link Pageable} contains pagination information
     * @return          the {@link Page} with {@link ReviewDTO} records
     */
    Page<ReviewDTO> getPendingReviews(Pageable pageable);

    /**
     * This method adds a review to the database
     *
     * @param reviewDTO the {@link ReviewDTO} containing review data
     * @return          the {@link ReviewDTO} saved in database
     */
    ReviewDTO add(ReviewDTO reviewDTO);

    /**
     * This method validates review by moderator
     *
     * @param reviewId      the {@link Long} review's id
     * @param moderatorId   the {@link Long} moderator's id
     * @return              the {@link ReviewDTO} updated in database
     */
    ReviewDTO validate(Long reviewId, Long moderatorId);

    /**
     * This method deletes a review from the database
     *
     * @param id    the {@link Long} review's id
     */
    void delete(Long id);

}
