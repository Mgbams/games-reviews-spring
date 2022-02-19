package fr.orsys.gamesreviews.repository;

import fr.orsys.gamesreviews.business.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findAllByGameIdAndModerationDateTimeIsNotNull(Long id, Pageable pageable);

    Page<Review> findAllByModerationDateTimeIsNotNull(Pageable pageable);

    Page<Review> findAllByModerationDateTimeIsNull(Pageable pageable);

    Optional<Review> findByGameIdAndPlayerId(Long gameId, Long playerId);

}
