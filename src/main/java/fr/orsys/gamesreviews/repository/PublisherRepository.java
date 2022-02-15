package fr.orsys.gamesreviews.repository;

import fr.orsys.gamesreviews.business.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    Optional<Publisher> getByName(String name);

}
