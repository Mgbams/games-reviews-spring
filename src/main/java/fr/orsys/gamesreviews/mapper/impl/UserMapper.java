package fr.orsys.gamesreviews.mapper.impl;

import org.springframework.stereotype.Component;

import fr.orsys.gamesreviews.business.user.Player;
import fr.orsys.gamesreviews.business.user.User;
import fr.orsys.gamesreviews.dto.UserDTO;
import fr.orsys.gamesreviews.mapper.Mapper;

@Component
public class UserMapper implements Mapper<User, UserDTO>{

	@Override
	public User mapDtoToEntity(UserDTO dto) {
		//TODO to implements reverse for entity User
		return null;
	}

	@Override
	public UserDTO mapEntityToDto(User user) {
		UserDTO userDTO = new UserDTO();
		
		userDTO.setId(user.getId());
		userDTO.setPseudonym(user.getPseudonym());
		userDTO.setEmail(user.getEmail());
		if(user instanceof Player) {
			userDTO.setRole(UserDTO.Role.Player);
		} else {
			userDTO.setRole(UserDTO.Role.Moderator);
		}
		return userDTO;
	}

	
}
