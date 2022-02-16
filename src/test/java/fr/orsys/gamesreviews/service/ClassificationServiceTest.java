package fr.orsys.gamesreviews.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import fr.orsys.gamesreviews.exception.RecordNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import fr.orsys.gamesreviews.business.Classification;
import fr.orsys.gamesreviews.exception.RecordAlreadyExistException;
import fr.orsys.gamesreviews.repository.ClassificationRepository;
import fr.orsys.gamesreviews.service.impl.ClassificationServiceImpl;

@ExtendWith(SpringExtension.class)
class ClassificationServiceTest {

	@InjectMocks
	private ClassificationServiceImpl classificationServiceImpl;

	@Mock
	private ClassificationRepository classificationRepository;
	
	@Test 
	void should_Count_Classifications_when_count() {
		long expected = 7;
		when(classificationRepository.count()).thenReturn(expected);
		
		long result = classificationServiceImpl.countClassifications();
		
		verify(classificationRepository).count();
		assertEquals(expected, result);
	}

    @Test
    void should_Throw_IllegalArgumentException_When_add_And_Classification_null() {
        assertThrows(IllegalArgumentException.class, () -> classificationServiceImpl.add(null));
    }

    @Test
    void should_throw_RecordAlreadyExistException_When_add_And_Classification_Exists() {
        String classificationName = "PEGI 12";
        Classification classification = new Classification();
        classification.setName(classificationName);
        when(classificationRepository.getByName(classificationName)).thenReturn(Optional.of(classification));

        assertThrows(RecordAlreadyExistException.class, () -> classificationServiceImpl.add(classification));
    }

    @Test
    void should_Add_Classification_When_add() {
        Classification expected = new Classification();
        expected.setName("PEGI 12");
        when(classificationRepository.save(any(Classification.class))).thenReturn(expected);

        Classification result = classificationServiceImpl.add(expected);

        verify(classificationRepository).save(expected);
        assertEquals(expected, result);
    }

    @Test
    void should_Throw_IllegalArgumentException_When_getById_And_Id_null() {
        assertThrows(IllegalArgumentException.class, () -> classificationServiceImpl.getById(null));
    }

    @Test
    void should_Throw_RecordNotFoundException_When_getById_And_Classification_NotFound() {
        Long id = 1L;
        when(classificationRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> classificationServiceImpl.getById(id));
    }

    @Test
    void should_Return_Classification_When_getById() {
        Long id = 1L;
        Classification expected = new Classification();
        expected.setId(id);
        when(classificationRepository.findById(any(Long.class))).thenReturn(Optional.of(expected));

        Classification result = classificationServiceImpl.getById(id);

        verify(classificationRepository).findById(id);
        assertEquals(expected, result);
    }

}
