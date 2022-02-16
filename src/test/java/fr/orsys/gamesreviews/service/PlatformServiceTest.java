package fr.orsys.gamesreviews.service;

import fr.orsys.gamesreviews.business.Platform;
import fr.orsys.gamesreviews.exception.RecordAlreadyExistException;
import fr.orsys.gamesreviews.exception.RecordNotFoundException;
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
    void should_Count_Platforms_When_count() {
        long expected = 5;
        when(platformRepository.count()).thenReturn(expected);

        long result = serviceUnderTest.countPlatforms();

        verify(platformRepository).count();
        assertEquals(expected, result);
    }

    @Test
    void should_Throw_IllegalArgumentException_When_add_And_Platform_null() {
        assertThrows(IllegalArgumentException.class, () -> serviceUnderTest.add(null));
    }

    @Test
    void should_Throw_RecordAlreadyExistException_When_add_And_Platform_Exists() {
        String platformName = "Platform";
        Platform platform = new Platform();
        platform.setName(platformName);
        when(platformRepository.getByName(platformName)).thenReturn(Optional.of(platform));

        assertThrows(RecordAlreadyExistException.class, () -> serviceUnderTest.add(platform));
    }

    @Test
    void should_Add_Platform_When_add() {
        Platform expected = new Platform();
        expected.setName("Platform");
        when(platformRepository.save(any(Platform.class))).thenReturn(expected);

        Platform result = serviceUnderTest.add(expected);

        verify(platformRepository).save(expected);
        assertEquals(expected, result);
    }

    @Test
    void should_Throw_IllegalArgumentException_When_getById_And_Id_null() {
        assertThrows(IllegalArgumentException.class, () -> serviceUnderTest.getById(null));
    }

    @Test
    void should_Throw_RecordNotFoundException_When_getById_And_Platform_NotFound() {
        Long id = 1L;
        when(platformRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> serviceUnderTest.getById(id));
    }

    @Test
    void should_Return_Platform_When_getById() {
        Long id = 1L;
        Platform expected = new Platform();
        expected.setId(id);
        when(platformRepository.findById(any(Long.class))).thenReturn(Optional.of(expected));

        Platform result = serviceUnderTest.getById(id);

        verify(platformRepository).findById(id);
        assertEquals(expected, result);
    }

}
