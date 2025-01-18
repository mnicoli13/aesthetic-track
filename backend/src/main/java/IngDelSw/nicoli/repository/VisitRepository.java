
package IngDelSw.nicoli.repository;

import IngDelSw.nicoli.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    Optional<Visit> findById(Integer id);

    List<Visit> findByPatientId(Integer patientId);
}
