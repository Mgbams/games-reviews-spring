package fr.orsys.gamesreviews.service.impl;

import org.springframework.stereotype.Service;

import fr.orsys.gamesreviews.business.user.Player;
import fr.orsys.gamesreviews.repository.PlayerRepository;
import fr.orsys.gamesreviews.service.PlayerService;
import lombok.AllArgsConstructor;

@AllArgsConstructor

@Service
public class PlayerServiceImpl implements PlayerService {
	

	private final PlayerRepository playerRepository;

	@Override
	public Player addPlayer(Player player) {
		return playerRepository.save(player);
	}

	@Override
	public Player getPlayer(String pseudonym) {
		return playerRepository.findByPseudonym(pseudonym);
	}

}