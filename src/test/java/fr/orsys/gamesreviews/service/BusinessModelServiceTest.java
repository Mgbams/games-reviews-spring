package fr.orsys.gamesreviews.service;

import fr.orsys.gamesreviews.business.BusinessModel;
import fr.orsys.gamesreviews.exception.RecordAlreadyExistException;
import fr.orsys.gamesreviews.exception.RecordNotFoundException;
import fr.orsys.gamesreviews.repository.BusinessModelRepository;
import fr.orsys.gamesreviews.service.impl.BusinessModelServiceImpl;
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
class BusinessModelServiceTest {

    @InjectMocks
    private BusinessModelServiceImpl serviceUnderTest;

    @Mock
    private BusinessModelRepository businessModelRepository;

    @Test
    void should_add_business_model() {
        BusinessModel expected = new BusinessModel();
        expected.setName("Model");
        when(businessModelRepository.save(any(BusinessModel.class))).thenReturn(expected);

        BusinessModel result = serviceUnderTest.addBusinessModel(expected);

        verify(businessModelRepository).save(expected);
        assertEquals(expected, result);
    }

    @Test
    void should_throw_RecordAlreadyExistException_when_business_model_exists() {
        String name = "Name";
        BusinessModel model = new BusinessModel();
        model.setName(name);
        when(businessModelRepository.getByName(name)).thenReturn(Optional.of(model));

        assertThrows(RecordAlreadyExistException.class, () -> serviceUnderTest.addBusinessModel(model));
    }

    @Test
    void should_return_business_model_by_name() {
        String name = "Name";
        BusinessModel expected = new BusinessModel();
        expected.setName(name);
        when(businessModelRepository.getByName(any(String.class))).thenReturn(Optional.of(expected));

        BusinessModel result = serviceUnderTest.getBusinessModelByName(name);

        verify(businessModelRepository).getByName(name);
        assertEquals(expected, result);
    }

    @Test
    void should_throw_RecordNotFoundException_when_genre_not_found() {
        String name = "Name";
        BusinessModel model = new BusinessModel();
        model.setName(name);
        when(businessModelRepository.getByName(name)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> serviceUnderTest.getBusinessModelByName(name));
    }

}
