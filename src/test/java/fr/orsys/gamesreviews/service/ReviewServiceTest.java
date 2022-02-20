package fr.orsys.gamesreviews.service;

import fr.orsys.gamesreviews.business.Review;
import fr.orsys.gamesreviews.business.user.Moderator;
import fr.orsys.gamesreviews.dto.ReviewDTO;
import fr.orsys.gamesreviews.exception.RecordAlreadyExistException;
import fr.orsys.gamesreviews.exception.RecordNotFoundException;
import fr.orsys.gamesreviews.mapper.Mapper;
import fr.orsys.gamesreviews.repository.ReviewRepository;
import fr.orsys.gamesreviews.service.impl.ReviewServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ReviewServiceTest {

    @InjectMocks
    ReviewServiceImpl serviceUnderTest;

    @Mock
    ReviewRepository reviewRepository;

    @Mock
    private Mapper<Review, ReviewDTO> mapper;

    @Test
    void should_Return_Page_Reviews_When_getValidatedReviews() {
        // Arrange
        ReviewDTO reviewDTO = new ReviewDTO();
        Page<ReviewDTO> expected = new PageImpl<>(List.of(reviewDTO));
        Review review = new Review();
        Page<Review> reviews = new PageImpl<>(List.of(review));
        Pageable request = PageRequest.of(0, 1);

        when(reviewRepository.findAllByModerationDateTimeIsNotNull(any(Pageable.class))).thenReturn(reviews);
        when(mapper.mapEntityToDto(any(Review.class))).thenReturn(reviewDTO);

        // Act
        Page<ReviewDTO> result = serviceUnderTest.getValidatedReviews(request);

        // Assert
        verify(reviewRepository).findAllByModerationDateTimeIsNotNull(request);
        verify(mapper, atLeastOnce()).mapEntityToDto(review);
        assertEquals(expected, result);
    }

    @Test
    void should_Throw_IllegalArgumentException_When_getValidatedReviewsByGame_And_GameId_null() {
        Pageable request = PageRequest.of(0, 1);
        assertThrows(IllegalArgumentException.class,
                () -> serviceUnderTest.getValidatedReviewsByGame(null, request));
    }

    @Test
    void should_Return_Page_Reviews_When_getValidatedReviewsByGame() {
        // Arrange
        Long gameId = 1L;
        ReviewDTO reviewDTO = new ReviewDTO();
        Page<ReviewDTO> expected = new PageImpl<>(List.of(reviewDTO));
        Review review = new Review();
        Page<Review> reviews = new PageImpl<>(List.of(review));
        Pageable request = PageRequest.of(0, 1);

        when(reviewRepository.findAllByGameIdAndModerationDateTimeIsNotNull(
                any(Long.class), any(Pageable.class))).thenReturn(reviews);
        when(mapper.mapEntityToDto(any(Review.class))).thenReturn(reviewDTO);

        // Act
        Page<ReviewDTO> result = serviceUnderTest.getValidatedReviewsByGame(gameId, request);

        // Assert
        verify(reviewRepository).findAllByGameIdAndModerationDateTimeIsNotNull(gameId, request);
        verify(mapper, atLeastOnce()).mapEntityToDto(review);
        assertEquals(expected, result);
    }

    @Test
    void should_Return_Page_Reviews_When_getPendingReviews() {
        // Arrange
        ReviewDTO reviewDTO = new ReviewDTO();
        Page<ReviewDTO> expected = new PageImpl<>(List.of(reviewDTO));
        Review review = new Review();
        Page<Review> reviews = new PageImpl<>(List.of(review));
        Pageable request = PageRequest.of(0, 1);

        when(reviewRepository.findAllByModerationDateTimeIsNull(any(Pageable.class))).thenReturn(reviews);
        when(mapper.mapEntityToDto(any(Review.class))).thenReturn(reviewDTO);

        // Act
        Page<ReviewDTO> result = serviceUnderTest.getPendingReviews(request);

        // Assert
        verify(reviewRepository).findAllByModerationDateTimeIsNull(request);
        verify(mapper, atLeastOnce()).mapEntityToDto(review);
        assertEquals(expected, result);
    }

    @Test
    void should_Throw_IllegalArgumentException_When_add_And_ReviewDTO_null() {
        assertThrows(IllegalArgumentException.class, () -> serviceUnderTest.add(null));
    }

    @Test
    void should_Throw_RecordAlreadyExistException_When_add_And_Review_From_Player_For_This_Game_Exists() {
        // Arrange
        Review review = new Review();
        Long gameId = 1L;
        Long playerId = 1L;
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setGame(new ReviewDTO.Game(gameId, "Game"));
        reviewDTO.setPlayer(new ReviewDTO.User(playerId, "Player"));

        when(reviewRepository.findByGameIdAndPlayerId(any(Long.class), any(Long.class)))
                .thenReturn(Optional.of(review));

        // Act Assert
        assertThrows(RecordAlreadyExistException.class, () -> serviceUnderTest.add(reviewDTO));
    }

    @Test
    void should_Return_ReviewDTO_When_add() {
        // Arrange
        Review review = new Review();
        Long gameId = 1L;
        Long playerId = 1L;
        ReviewDTO expected = new ReviewDTO();
        expected.setGame(new ReviewDTO.Game(gameId, "Game"));
        expected.setPlayer(new ReviewDTO.User(playerId, "Player"));

        when(reviewRepository.findByGameIdAndPlayerId(any(Long.class), any(Long.class)))
                .thenReturn(Optional.empty());
        when(mapper.mapDtoToEntity(any(ReviewDTO.class))).thenReturn(review);
        when(reviewRepository.save(any(Review.class))).thenReturn(review);
        when(mapper.mapEntityToDto(any(Review.class))).thenReturn(expected);

        // Act
        ReviewDTO result = serviceUnderTest.add(expected);

        // Assert
        verify(reviewRepository).findByGameIdAndPlayerId(gameId, playerId);
        verify(mapper).mapDtoToEntity(expected);
        verify(reviewRepository).save(review);
        verify(mapper).mapEntityToDto(review);
        assertEquals(expected, result);
    }

    @Test
    void should_Throw_IllegalArgumentException_When_validate_And_ReviewDTO_null() {
        assertThrows(IllegalArgumentException.class, () -> serviceUnderTest.validate(null));
    }

    @Test
    void should_Throw_IllegalArgumentException_When_validate_And_Moderator_null() {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(1L);
        assertThrows(IllegalArgumentException.class, () -> serviceUnderTest.validate(reviewDTO));
    }

    @Test
    void should_Throw_RecordNotFoundException_When_validate_And_Review_NotFound() {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(1L);
        reviewDTO.setModerator(new ReviewDTO.User(1L, "Moderator"));
        when(reviewRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> serviceUnderTest.validate(reviewDTO));
    }

    @Test
    void should_Return_Valid_Review_When_validate() {
        Moderator moderator = new Moderator();
        moderator.setId(1L);
        moderator.setPseudonym("Moderator");

        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(1L);
        reviewDTO.setModerator(new ReviewDTO.User(moderator.getId(), moderator.getPseudonym()));

        Review beforeValidation = new Review();
        beforeValidation.setId(reviewDTO.getId());

        Review afterValidation = new Review();
        afterValidation.setId(beforeValidation.getId());
        afterValidation.setModerator(moderator);
        afterValidation.setModerationDateTime(LocalDateTime.now());

        ReviewDTO expected = new ReviewDTO();
        expected.setId(afterValidation.getId());
        expected.setModerator(new ReviewDTO.User(
                afterValidation.getModerator().getId(),
                afterValidation.getModerator().getPseudonym()
        ));
        expected.setModerationDateTime(afterValidation.getModerationDateTime());

        when(reviewRepository.findById(any(Long.class))).thenReturn(Optional.of(beforeValidation));
        when(reviewRepository.save(any(Review.class))).thenReturn(afterValidation);
        when(mapper.mapEntityToDto(any(Review.class))).thenReturn(expected);

        ReviewDTO result = serviceUnderTest.validate(reviewDTO);

        verify(reviewRepository).save(beforeValidation);
        verify(mapper).mapEntityToDto(afterValidation);
        assertEquals(expected, result);
    }

    @Test
    void should_Throw_RecordNotFoundException_When_delete_And_Review_NotFound() {
        long id = 1;
        when(reviewRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> serviceUnderTest.delete(id));
    }

    @Test
    void should_Delete_Review_When_deleteById() {
        // Arrange
        long id = 1;
        Review review = new Review();
        review.setId(id);

        when(reviewRepository.findById(any(Long.class))).thenReturn(Optional.of(review));

        // Act
        serviceUnderTest.delete(id);

        // Assert
        verify(reviewRepository, times(1)).deleteById(id);
    }

}
