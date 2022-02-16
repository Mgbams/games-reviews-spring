package fr.orsys.gamesreviews.controller;

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
	
	@PostMapping("/player")
	public Player addPlayer(@RequestBody Player player) {
		return playerService.addPlayer(player);
	}
	
	
}
