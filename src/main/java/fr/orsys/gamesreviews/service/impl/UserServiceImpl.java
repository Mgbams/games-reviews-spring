package fr.orsys.gamesreviews.service.impl;

import org.springframework.stereotype.Service;

import fr.orsys.gamesreviews.business.user.Player;
import fr.orsys.gamesreviews.business.user.User;
import fr.orsys.gamesreviews.dto.UserDTO;
import fr.orsys.gamesreviews.exception.RecordNotFoundException;
import fr.orsys.gamesreviews.mapper.Mapper;
import fr.orsys.gamesreviews.repository.UserRepository;
import fr.orsys.gamesreviews.service.UserService;
import lombok.AllArgsConstructor;

@AllArgsConstructor

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final Mapper<User, UserDTO> mapper; 

	
	   @Override
	    public boolean existsByPseudonym(String pseudonym) {
	        return userRepository.existsByPseudonym(pseudonym);
	    }

	    @Override
	    public boolean existsByEmail(String email) {
	        return userRepository.existsByEmail(email);
	    }

		@Override
		public UserDTO findByPseudonym(String pseudonym) {
			if(pseudonym == null) {
				throw new IllegalArgumentException("User must not be null");
			}
			User user = userRepository.findByPseudonym(pseudonym)
					.orElseThrow(() -> new RecordNotFoundException("User by pseudonym : "+  pseudonym + " not found"));
			return mapper.mapEntityToDto(user);
		}
}
