package fr.orsys.gamesreviews.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.orsys.gamesreviews.business.user.Player;
import fr.orsys.gamesreviews.service.PlayerService;
import lombok.AllArgsConstructor;

@AllArgsConstructor

@RestController
@RequestMapping("api/player")
public class PlayerController {

	private final PlayerService playerService;
	
	@RequestMapping("/login")
	public boolean getPlayer(@RequestBody Player player) {
		if (playerService.getPlayer(player.getPseudonym()) != null) {
			if (player.getPassword().equals("password")) {
				return true;
			} else { 
				return false;
			}
		} else {
			return false;
		}
	}
	
	@PostMapping("/signup")
	public ResponseEntity<Player> addPlayer(@Valid @RequestBody Player player) {
		//PLayer newPlayer = new Player();
        return new ResponseEntity<>(playerService.addPlayer(player), HttpStatus.CREATED);
	}
}