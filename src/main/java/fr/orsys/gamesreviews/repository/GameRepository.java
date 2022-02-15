package fr.orsys.gamesreviews.repository;

import fr.orsys.gamesreviews.business.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {

    Optional<Object> findByName(String name);

}
