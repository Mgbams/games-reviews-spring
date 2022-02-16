package fr.orsys.gamesreviews.service.impl;

import org.springframework.stereotype.Service;

import fr.orsys.gamesreviews.repository.UserRepository;
import fr.orsys.gamesreviews.service.UserService;
import lombok.AllArgsConstructor;

@AllArgsConstructor

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	
	   @Override
	    public boolean existsByPseudonym(String pseudonym) {
	        return userRepository.existsByPseudonym(pseudonym);
	    }

	    @Override
	    public boolean existsByEmail(String email) {
	        return userRepository.existsByEmail(email);
	    }

}
