package fr.orsys.gamesreviews.service;

import fr.orsys.gamesreviews.business.Platform;
import fr.orsys.gamesreviews.exception.RecordAlreadyExistException;
import fr.orsys.gamesreviews.repository.PlatformRepository;
import fr.orsys.gamesreviews.service.impl.PlatformServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class PlatformServiceTest {

    @InjectMocks
    private PlatformServiceImpl serviceUnderTest;

    @Mock
    private PlatformRepository platformRepository;

    @Test
    void should_count_platforms() {
        // Arrange
        long expected = 5;
        when(platformRepository.count()).thenReturn(expected);

        // Act
        long result = serviceUnderTest.countPlatforms();

        // Assert
        verify(platformRepository).count();
        assertEquals(expected, result);
    }

    @Test
    void should_add_platform() {
        Platform expected = new Platform();
        expected.setName("Platform");
        when(platformRepository.save(any(Platform.class))).thenReturn(expected);

        Platform result = serviceUnderTest.addPlatform(expected);

        verify(platformRepository).save(expected);
        assertEquals(expected, result);
    }

    @Test
    void should_throw_RecordAlreadyExistException_when_record_exists() {
        String platformName = "Platform";
        Platform platform = new Platform();
        platform.setName(platformName);
        when(platformRepository.getByName(platformName)).thenReturn(Optional.of(platform));

        assertThrows(RecordAlreadyExistException.class, () -> serviceUnderTest.addPlatform(platform));
    }

}
