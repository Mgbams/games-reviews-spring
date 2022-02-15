package fr.orsys.gamesreviews.repository;

import fr.orsys.gamesreviews.business.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    Optional<Genre> getByName(String name);

}
