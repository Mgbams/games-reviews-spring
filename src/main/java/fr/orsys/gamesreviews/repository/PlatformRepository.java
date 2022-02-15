package fr.orsys.gamesreviews.repository;

import fr.orsys.gamesreviews.business.Platform;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlatformRepository extends JpaRepository<Platform, Long> {

    Optional<Object> getByName(String name);

}
