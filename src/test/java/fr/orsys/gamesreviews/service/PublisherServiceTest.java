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
    void should_add_publisher() {
        Publisher expected = new Publisher();
        expected.setName("Publisher");
        when(publisherRepository.save(any(Publisher.class))).thenReturn(expected);

        Publisher result = serviceUnderTest.addPublisher(expected);

        verify(publisherRepository).save(expected);
        assertEquals(expected, result);
    }

    @Test
    void should_throw_RecordAlreadyExistException_when_publisher_exists() {
        String name = "Name";
        Publisher publisher = new Publisher();
        publisher.setName(name);
        when(publisherRepository.getByName(name)).thenReturn(Optional.of(publisher));

        assertThrows(RecordAlreadyExistException.class, () -> serviceUnderTest.addPublisher(publisher));
    }

    @Test
    void should_return_publisher_by_name() {
        String name = "Name";
        Publisher expected = new Publisher();
        expected.setName(name);
        when(publisherRepository.getByName(any(String.class))).thenReturn(Optional.of(expected));

        Publisher result = serviceUnderTest.getPublisherByName(name);

        verify(publisherRepository).getByName(name);
        assertEquals(expected, result);
    }

    @Test
    void should_throw_RecordNotFoundException_when_genre_not_found() {
        String name = "Name";
        Publisher publisher = new Publisher();
        publisher.setName(name);
        when(publisherRepository.getByName(name)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> serviceUnderTest.getPublisherByName(name));
    }

}
