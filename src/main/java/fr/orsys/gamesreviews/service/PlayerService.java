package fr.orsys.gamesreviews.service;

import fr.orsys.gamesreviews.business.user.Player;


public interface PlayerService {

	Player addPlayer(Player player);
	
	Player getPlayer(String pseudonym);
}
