package fr.orsys.gamesreviews.service.impl;

import org.springframework.stereotype.Service;

import fr.orsys.gamesreviews.business.user.Player;
import fr.orsys.gamesreviews.repository.PlayerRepository;
import fr.orsys.gamesreviews.service.PlayerService;
import lombok.AllArgsConstructor;

@AllArgsConstructor

@Service
public class PlayerServiceImpl implements PlayerService {

	private PlayerRepository playerRepositiory;
	
	// Remplacé par le AllArgConstructor
//	public PlayerServiceImpl(PlayerRepository playerRepositiory) {
//		super();
//		this.playerRepositiory = playerRepositiory;
//	}

	@Override
	public Player addPlayer(Player player) {
		return playerRepositiory.save(player);
	}

	@Override
	public Player getPlayer(String pseudonym) {
		return playerRepositiory.findByPseudonym(pseudonym);
	}

}