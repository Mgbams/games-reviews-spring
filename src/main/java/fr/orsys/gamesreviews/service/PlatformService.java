package fr.orsys.gamesreviews.service;

import java.util.List;

import fr.orsys.gamesreviews.business.Platform;

public interface PlatformService {

    long countPlatforms();

    Platform add(Platform platform);

    Platform getById(Long id);

	List<Platform> findAll();

}
