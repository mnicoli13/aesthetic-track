
package IngDelSw.nicoli.repository;

import IngDelSw.nicoli.model.Patient;
import IngDelSw.nicoli.model.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TreatmentRepository extends JpaRepository<Treatment, Long> {
    Optional<Treatment> findById(Integer id);
    List<Treatment> findByPatientId(Integer patientId);
}
