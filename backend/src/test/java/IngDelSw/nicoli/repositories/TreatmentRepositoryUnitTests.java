package IngDelSw.nicoli.repositories;

import IngDelSw.nicoli.model.Patient;
import IngDelSw.nicoli.model.Treatment;
import IngDelSw.nicoli.repository.TreatmentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TreatmentRepositoryUnitTests {

    @Autowired
    private TreatmentRepository treatmentRepository;
    private static Integer treatmentId;

    @Test
    @DisplayName("Test 1: Save Treatment Test")
    @Order(1)
    @Rollback(value = false)
    public void saveTreatmentTest() {

        Treatment treatment = new Treatment();
        treatment.setPatientId(1);
        treatment.setMotivationTitle("Facial treatment");

        Treatment createdTreatment = treatmentRepository.save(treatment);
        treatmentId = createdTreatment.getId();

        System.out.println(createdTreatment);
        Assertions.assertThat(createdTreatment.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getTreatmentTest() {

        Treatment treatment = treatmentRepository.findById(treatmentId).get();

        System.out.println(treatment);
        Assertions.assertThat(treatment.getId()).isEqualTo(treatmentId);
    }

    @Test
    @Order(3)
    public void getTreatmentsByPatientIdTest() {

        List<Treatment> treatments = treatmentRepository.findByPatientId(1);  // Assumendo che un ID paziente valido esista

        System.out.println(treatments);
        Assertions.assertThat(treatments.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    public void getTreatmentNotFoundTest() {

        Optional<Treatment> treatment = treatmentRepository.findById(999);  // Assumendo che 999 non esista

        Assertions.assertThat(treatment).isEmpty();
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void updateTreatmentTest() {

        Treatment treatment = treatmentRepository.findById(treatmentId).get();
        treatment.setMotivationDesc("Updated facial treatment description");
        Treatment updatedTreatment = treatmentRepository.save(treatment);

        System.out.println(updatedTreatment);
        Assertions.assertThat(updatedTreatment.getMotivationDesc()).isEqualTo("Updated facial treatment description");
    }

    @Test
    @Order(6)
    @Rollback(value = false)
    public void deleteTreatmentTest() {

        treatmentRepository.deleteById((long) treatmentId);

        Assertions.assertThat(treatmentRepository.findById(treatmentId)).isEmpty();
    }
}
