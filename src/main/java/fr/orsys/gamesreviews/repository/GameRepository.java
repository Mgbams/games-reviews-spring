package fr.orsys.gamesreviews.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import fr.orsys.gamesreviews.business.Game;

public interface GameRepository extends JpaRepository<Game, Long> {

    Optional<Object> findByName(String name);
    
    @Modifying
    @Transactional 
    @Query("UPDATE Game g SET g.picture = :picture WHERE g.id = :id")
    void updateGamePicture(@Param("id") Long id, @Param("picture") String picture);

}
