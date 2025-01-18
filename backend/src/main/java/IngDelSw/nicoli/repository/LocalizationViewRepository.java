
package IngDelSw.nicoli.repository;

import IngDelSw.nicoli.model.LocalizationView;
import IngDelSw.nicoli.model.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocalizationViewRepository extends JpaRepository<LocalizationView, Long> {
    Optional<LocalizationView> findById(Integer id);
    List<LocalizationView> findByLocalizationId(Integer localizationId);
}
