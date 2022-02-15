package fr.orsys.gamesreviews.service.impl;

import fr.orsys.gamesreviews.business.Platform;
import fr.orsys.gamesreviews.exception.RecordAlreadyExistException;
import fr.orsys.gamesreviews.repository.PlatformRepository;
import fr.orsys.gamesreviews.service.PlatformService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor

@Service
public class PlatformServiceImpl implements PlatformService {

    private final PlatformRepository platformRepository;

    @Override
    public long countPlatforms() {
        return platformRepository.count();
    }

    @Override
    public Platform addPlatform(Platform platform) {
        if (platformRepository.getByName(platform.getName()).isPresent()) {
            throw new RecordAlreadyExistException("Platform with name \"" + platform.getName() + "\" already exists");
        }
        return platformRepository.save(platform);
    }

}
