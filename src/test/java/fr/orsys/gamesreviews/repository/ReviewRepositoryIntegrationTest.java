package fr.orsys.gamesreviews.repository;

import fr.orsys.gamesreviews.business.Review;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Sql("/review_repository_test.sql")
@DataJpaTest
@ExtendWith(SpringExtension.class)
class ReviewRepositoryIntegrationTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    ReviewRepository repositoryUnderTest;

    @Test
    void should_Return_Page_Reviews_hen_findAllByGameIdAndModerationDateTimeIsNotNull() {
        Long elements = 1L;
        Long gameId = 1L;
        Pageable request = PageRequest.of(0, 10);
        Page<Review> result = repositoryUnderTest.findAllByGameIdAndModerationDateTimeIsNotNull(gameId, request);
        assertEquals(elements, result.getTotalElements());
    }

    @Test
    void should_Return_Page_Reviews_hen_findAllByModerationDateTimeIsNotNull() {
        Long elements = 1L;
        Pageable request = PageRequest.of(0, 10);
        Page<Review> result = repositoryUnderTest.findAllByModerationDateTimeIsNotNull(request);
        assertEquals(elements, result.getTotalElements());
    }

    @Test
    void should_Return_Page_Reviews_hen_findAllByModerationDateTimeIsNull() {
        Long elements = 1L;
        Pageable request = PageRequest.of(0, 10);
        Page<Review> result = repositoryUnderTest.findAllByModerationDateTimeIsNull(request);
        assertEquals(elements, result.getTotalElements());
    }

    @Test
    void should_Return_Page_Reviews_hen_findByGameIdAndPlayerId() {
        Long gameId = 2L;
        Long playerId = 2L;
        Optional<Review> result = repositoryUnderTest.findByGameIdAndPlayerId(gameId, playerId);
        assertTrue(result.isPresent());
    }

}
