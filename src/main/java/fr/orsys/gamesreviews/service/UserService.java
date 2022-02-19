package fr.orsys.gamesreviews.service;

import fr.orsys.gamesreviews.dto.UserDTO;

public interface UserService {

	boolean existsByPseudonym(String pseudonym);

	boolean existsByEmail(String email);

	UserDTO findByPseudonym(String pseudonym);
}
