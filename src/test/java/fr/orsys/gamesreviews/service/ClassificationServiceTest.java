package fr.orsys.gamesreviews.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

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
	void should_count_classification() {
		// Setup default value
		long expected = 7;
		when(classificationRepository.count()).thenReturn(expected);
		
		// Result value from service
		long result = classificationServiceImpl.countClassifications();
		
		// Confirmation of data
		verify(classificationRepository).count();
		assertEquals(expected, result);
	}
	
    @Test
    void should_add_classification() {
        Classification expected = new Classification();
        expected.setName("PEGI 12");
        when(classificationRepository.save(any(Classification.class))).thenReturn(expected);

        Classification result = classificationServiceImpl.addClassification(expected);

        verify(classificationRepository).save(expected);
        assertEquals(expected, result);
    }

    @Test
    void should_throw_RecordAlreadyExistException_when_record_exists() {
        String classificationName = "PEGI 12";
        Classification classification = new Classification();
        classification.setName(classificationName);
        when(classificationRepository.getByName(classificationName)).thenReturn(Optional.of(classification));

        assertThrows(RecordAlreadyExistException.class, () -> classificationServiceImpl.addClassification(classification));
    }
	
}
