package fr.orsys.gamesreviews.service;

public interface UserService {

	boolean existsByPseudonym(String pseudonym);

	boolean existsByEmail(String email);

}
