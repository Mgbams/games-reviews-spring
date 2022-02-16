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
    public Publisher add(Publisher publisher) {
        if (publisher == null) {
            throw new IllegalArgumentException("Publisher must not be null");
        }

        if (publisherRepository.getByName(publisher.getName()).isPresent()) {
            throw new RecordAlreadyExistException("Publisher with name \"" + publisher.getName() + "\" already exists");
        }
        return publisherRepository.save(publisher);
    }

    @Override
    public Publisher getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Publisher id must not be null");
        }
        return publisherRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Could not find publisher with id  " + id));
    }

}
