package IngDelSw.nicoli.repository;

import IngDelSw.nicoli.model.Localization;
import IngDelSw.nicoli.model.Treatment;
import IngDelSw.nicoli.model.enums.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocalizationRepository extends JpaRepository<Localization, Long> {
    Optional<Localization> findById(Integer id);
    List<Localization> findByGender(Gender gender);

}
