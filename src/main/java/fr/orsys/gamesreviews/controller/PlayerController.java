package fr.orsys.gamesreviews.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.orsys.gamesreviews.business.user.Player;
import fr.orsys.gamesreviews.business.user.User;
import fr.orsys.gamesreviews.dto.LoginDTO;
import fr.orsys.gamesreviews.dto.UserDTO;
import fr.orsys.gamesreviews.service.PlayerService;
import fr.orsys.gamesreviews.service.UserService;
import lombok.AllArgsConstructor;

@AllArgsConstructor

@RestController
@RequestMapping("api/player")
@CrossOrigin(origins = {"http://localhost:4200"})
public class PlayerController {

	private final PlayerService playerService;
	
	private final UserService userService;
	
//	@RequestMapping("/login")
//	public boolean getPlayer(@RequestBody Player player) {
//		if (playerService.getPlayer(player.getPseudonym()) != null) {
//			if (player.getPassword().equals("password")) {
//				return true;
//			} else { 
//				return false;
//			}
//		} else {
//			return false;
//		}
//	}

	@RequestMapping("/login")
	public ResponseEntity<UserDTO> getUser(@RequestBody LoginDTO login) {
		UserDTO userFind = userService.findByPseudonym(login.getPseudonym());
		return new ResponseEntity<>(userFind, HttpStatus.OK);
		
		
//		return userFind;
//		if(userFind != null && user.getPassword().equals(userFind.getPassword())) {
//			if(userFind instanceof Player) {
//				System.out.println("Player");
//			} 
//			
//		} else {
//			return null;
//		}

//		if (userFind != null) {
//			if (user.getPassword().equals(userFind.getPassword())) {
//				if(userFind instanceof Player) {
//					return (Player)user;
//				} else {
//					return (Moderator)user;
//				}
//			} else { 
//				return null;
//			}
//		} else {
//			return null;
//		}
	}

	@PostMapping("/signup")
	public Player addPlayer(@RequestBody Player player) {
		return playerService.addPlayer(player);
	}
}