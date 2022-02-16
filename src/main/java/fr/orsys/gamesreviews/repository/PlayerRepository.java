package fr.orsys.gamesreviews.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.orsys.gamesreviews.business.user.Player;

public interface PlayerRepository extends JpaRepository<Player, Long>{

	Player findByPseudonym(String pseudonym);

}