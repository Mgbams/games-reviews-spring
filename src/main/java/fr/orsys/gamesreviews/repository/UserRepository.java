package fr.orsys.gamesreviews.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.orsys.gamesreviews.business.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByPseudonym(String pseudonym);

	boolean existsByEmail(String email);
	
	Optional<User> findByPseudonym(String pseudonym);
}
