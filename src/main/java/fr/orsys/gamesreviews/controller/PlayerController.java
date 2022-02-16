package fr.orsys.gamesreviews.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.orsys.gamesreviews.business.user.Player;
import fr.orsys.gamesreviews.service.PlayerService;
import lombok.AllArgsConstructor;

@AllArgsConstructor

@RestController
@RequestMapping("api/player")
@CrossOrigin(origins = {"http://localhost:4200"})
public class PlayerController {

	private final PlayerService playerService;
	
	@RequestMapping("/login")
	public boolean getPlayer(@RequestBody Player player) {
		// TODO : regarder Si le pseudo du joueur existe en base
		// TODO : comparer les deux MdP s'ils sont
		return player.getPseudonym().equals("user") && player.getPassword().equals("password");
	}
	
}
