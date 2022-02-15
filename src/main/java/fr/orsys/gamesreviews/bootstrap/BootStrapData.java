package fr.orsys.gamesreviews.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import fr.orsys.gamesreviews.business.Classification;
import fr.orsys.gamesreviews.business.Platform;
import fr.orsys.gamesreviews.service.ClassificationService;
import fr.orsys.gamesreviews.service.PlatformService;
import lombok.AllArgsConstructor;

@AllArgsConstructor

@Component
public class BootStrapData implements CommandLineRunner {

    private final PlatformService platformService;
    private final ClassificationService classificationService;

    
    
    @Override
    public void run(String... args) {
        loadPlatforms();
        loadClassifications();
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
    
    private void loadClassifications() {
        if (classificationService.countClassifications() == 0) {
            int[] ages = {3, 7, 12, 16, 18};
            for (int age : ages) {
                Classification classification = new Classification();
                classification.setName("PEGI " + age);
                classificationService.addClassification(classification);
            }
        }
    }

}
