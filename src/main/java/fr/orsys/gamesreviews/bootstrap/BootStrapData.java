package fr.orsys.gamesreviews.bootstrap;

import fr.orsys.gamesreviews.business.Platform;
import fr.orsys.gamesreviews.service.PlatformService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@AllArgsConstructor

@Component
public class BootStrapData implements CommandLineRunner {

    private final PlatformService platformService;

    @Override
    public void run(String... args) {
        loadPlatforms();
    }

    private void loadPlatforms() {
        if (platformService.countPlatforms() == 0) {
            String[] platforms = {"Windows", "macOS", "Linux", "Android", "iOS",
                    "Playstation 3", "Playstation 4", "Xbox ONE", "Xbox 360"};
            for (String name : platforms) {
                Platform platform = new Platform();
                platform.setName(name);
                platformService.addPlatform(platform);
            }
        }
    }

}
