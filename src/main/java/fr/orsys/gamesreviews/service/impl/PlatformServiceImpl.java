package fr.orsys.gamesreviews.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.orsys.gamesreviews.business.Platform;
import fr.orsys.gamesreviews.exception.RecordAlreadyExistException;
import fr.orsys.gamesreviews.exception.RecordNotFoundException;
import fr.orsys.gamesreviews.repository.PlatformRepository;
import fr.orsys.gamesreviews.service.PlatformService;
import lombok.AllArgsConstructor;

@AllArgsConstructor

@Service
public class PlatformServiceImpl implements PlatformService {

    private final PlatformRepository platformRepository;

    @Override
    public long countPlatforms() {
        return platformRepository.count();
    }

    @Override
    public Platform add(Platform platform) {
        if (platform == null) {
            throw new IllegalArgumentException("Platform must not be null");
        }

        if (platformRepository.getByName(platform.getName()).isPresent()) {
            throw new RecordAlreadyExistException("Platform with name \"" + platform.getName() + "\" already exists");
        }
        return platformRepository.save(platform);
    }

    @Override
    public Platform getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Platform id must not be null");
        }
        return platformRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Could not find platform with id  " + id));
    }
    
    @Override
    public List<Platform> findAll() {
        return platformRepository.findAll();
    }

}
