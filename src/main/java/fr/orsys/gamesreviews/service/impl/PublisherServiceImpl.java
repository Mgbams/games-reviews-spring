package fr.orsys.gamesreviews.service.impl;

import fr.orsys.gamesreviews.business.Publisher;
import fr.orsys.gamesreviews.exception.RecordAlreadyExistException;
import fr.orsys.gamesreviews.exception.RecordNotFoundException;
import fr.orsys.gamesreviews.repository.PublisherRepository;
import fr.orsys.gamesreviews.service.PublisherService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor

@Service
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    @Override
    public Publisher addPublisher(Publisher publisher) {
        if (publisherRepository.getByName(publisher.getName()).isPresent()) {
            throw new RecordAlreadyExistException("Publisher with name \"" + publisher.getName() + "\" already exists");
        }
        return publisherRepository.save(publisher);
    }

    @Override
    public Publisher getPublisherByName(String name) {
        return publisherRepository.getByName(name)
                .orElseThrow(() ->new RecordNotFoundException("Could not find publisher named " + name));
    }

}
