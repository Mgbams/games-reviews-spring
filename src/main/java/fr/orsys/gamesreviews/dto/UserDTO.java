package fr.orsys.gamesreviews.dto;

import lombok.Data;

@Data
public class UserDTO {

	public enum Role{Player, Moderator}
	
	private Long id;
	
	private String pseudonym;
	
	private String email;
	
	private Role role;
}
