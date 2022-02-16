package fr.orsys.gamesreviews.service;

import java.util.Optional;

import fr.orsys.gamesreviews.business.user.Player;

public interface PlayerService {

	Player addPlayer(Player player);
	
	Player getPlayer(String pseudonym);
}
