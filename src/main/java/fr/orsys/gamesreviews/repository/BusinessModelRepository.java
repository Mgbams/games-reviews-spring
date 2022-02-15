package fr.orsys.gamesreviews.repository;

import fr.orsys.gamesreviews.business.BusinessModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusinessModelRepository extends JpaRepository<BusinessModel, Long> {

    Optional<BusinessModel> getByName(String name);

}