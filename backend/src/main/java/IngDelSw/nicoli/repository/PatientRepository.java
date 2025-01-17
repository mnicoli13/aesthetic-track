
package IngDelSw.nicoli.repository;

import IngDelSw.nicoli.model.Patient;
import IngDelSw.nicoli.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findById(Integer id);

    List<Patient> findByCreatedBy(Integer created_by);
}
