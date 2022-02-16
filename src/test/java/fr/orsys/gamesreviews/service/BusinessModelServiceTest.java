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
    void should_Throw_IllegalArgumentException_When_add_And_BusinessModel_null() {
        assertThrows(IllegalArgumentException.class, () -> serviceUnderTest.add(null));
    }

    @Test
    void should_Throw_RecordAlreadyExistException_When_add_And_BusinessModel_Exists() {
        String name = "Name";
        BusinessModel model = new BusinessModel();
        model.setName(name);
        when(businessModelRepository.getByName(name)).thenReturn(Optional.of(model));

        assertThrows(RecordAlreadyExistException.class, () -> serviceUnderTest.add(model));
    }

    @Test
    void should_Add_BusinessModel_When_add() {
        BusinessModel expected = new BusinessModel();
        expected.setName("Model");
        when(businessModelRepository.save(any(BusinessModel.class))).thenReturn(expected);

        BusinessModel result = serviceUnderTest.add(expected);

        verify(businessModelRepository).save(expected);
        assertEquals(expected, result);
    }

    @Test
    void should_Throw_IllegalArgumentException_When_getById_And_Id_null() {
        assertThrows(IllegalArgumentException.class, () -> serviceUnderTest.getById(null));
    }

    @Test
    void should_Throw_RecordNotFoundException_When_getById_And_BusinessModel_NotFound() {
        Long id = 1L;
        when(businessModelRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> serviceUnderTest.getById(id));
    }

    @Test
    void should_Return_BusinessModel_When_getById() {
        Long id = 1L;
        BusinessModel expected = new BusinessModel();
        expected.setId(id);
        when(businessModelRepository.findById(any(Long.class))).thenReturn(Optional.of(expected));

        BusinessModel result = serviceUnderTest.getById(id);

        verify(businessModelRepository).findById(id);
        assertEquals(expected, result);
    }

}
