package fr.orsys.gamesreviews.service;

import fr.orsys.gamesreviews.business.Publisher;
import fr.orsys.gamesreviews.exception.RecordAlreadyExistException;
import fr.orsys.gamesreviews.exception.RecordNotFoundException;
import fr.orsys.gamesreviews.repository.PublisherRepository;
import fr.orsys.gamesreviews.service.impl.PublisherServiceImpl;
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
class PublisherServiceTest {

    @InjectMocks
    private PublisherServiceImpl serviceUnderTest;

    @Mock
    private PublisherRepository publisherRepository;

    @Test
    void should_Throw_IllegalArgumentException_When_add_And_Publisher_null() {
        assertThrows(IllegalArgumentException.class, () -> serviceUnderTest.add(null));
    }

    @Test
    void should_Throw_RecordAlreadyExistException_When_add_And_Publisher_Exists() {
        String name = "Name";
        Publisher publisher = new Publisher();
        publisher.setName(name);
        when(publisherRepository.getByName(name)).thenReturn(Optional.of(publisher));

        assertThrows(RecordAlreadyExistException.class, () -> serviceUnderTest.add(publisher));
    }

    @Test
    void should_Add_Publisher_When_add() {
        Publisher expected = new Publisher();
        expected.setName("Publisher");
        when(publisherRepository.save(any(Publisher.class))).thenReturn(expected);

        Publisher result = serviceUnderTest.add(expected);

        verify(publisherRepository).save(expected);
        assertEquals(expected, result);
    }

    @Test
    void should_Throw_IllegalArgumentException_When_getById_And_Id_null() {
        assertThrows(IllegalArgumentException.class, () -> serviceUnderTest.getById(null));
    }

    @Test
    void should_Throw_RecordNotFoundException_When_getById_And_Publisher_NotFound() {
        Long id = 1L;
        when(publisherRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> serviceUnderTest.getById(id));
    }

    @Test
    void should_Return_Publisher_When_getById() {
        Long id = 1L;
        Publisher expected = new Publisher();
        expected.setId(id);
        when(publisherRepository.findById(any(Long.class))).thenReturn(Optional.of(expected));

        Publisher result = serviceUnderTest.getById(id);

        verify(publisherRepository).findById(id);
        assertEquals(expected, result);
    }

}
