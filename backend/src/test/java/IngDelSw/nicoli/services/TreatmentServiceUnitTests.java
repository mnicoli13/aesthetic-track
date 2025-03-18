package IngDelSw.nicoli.services;


import IngDelSw.nicoli.model.Treatment;
import IngDelSw.nicoli.repository.TreatmentRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import(TreatmentService.class)

public class TreatmentServiceUnitTests {

    @Autowired
    private TreatmentRepository treatmentRepository;

    @Autowired
    private TreatmentService treatmentService;

    private static Integer treatmentId;

    @Test
    @DisplayName("Test 1: Save Treatment Test")
    @Order(1)
    @Rollback(value = false)
    public void saveTreatmentTest() {
        Treatment treatment = new Treatment();
        treatment.setPatientId(1);  // Assuming a valid patient ID exists
        treatment.setMotivationTitle("Cosmetic Enhancement");
        treatment.setMotivationDesc("Improving appearance of facial features");
        treatment.setLocalizationId(2);  // Assuming a valid localization ID exists

        Treatment createdTreatment = treatmentService.storeTreatment(1, "Cosmetic Enhancement", "Improving appearance of facial features", 2);
        treatmentId = createdTreatment.getId();

        System.out.println(createdTreatment);
        Assertions.assertThat(createdTreatment.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getTreatmentTest() {
        Treatment treatment = treatmentService.getTreatment(treatmentId);

        System.out.println(treatment);
        Assertions.assertThat(treatment.getId()).isEqualTo(treatmentId);
    }

    @Test
    @Order(3)
    public void getTreatmentsByPatientIdTest() {
        List<Treatment> treatments = treatmentService.getTreatmentsByPatientId(1);  // Assuming a valid patient ID exists

        System.out.println(treatments);
        Assertions.assertThat(treatments.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateTreatmentTest() {
        Treatment treatment = treatmentService.getTreatment(treatmentId);
        treatment.setMotivationTitle("Updated Cosmetic Enhancement");
        Treatment updatedTreatment = treatmentService.updateTreatment(treatmentId, 1, "Updated Cosmetic Enhancement", "Updated motivation description", 2);

        System.out.println(updatedTreatment);
        Assertions.assertThat(updatedTreatment.getMotivationTitle()).isEqualTo("Updated Cosmetic Enhancement");
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteTreatmentTest() {
        treatmentService.deleteTreatment(treatmentId);

        Assertions.assertThat(treatmentRepository.findById(treatmentId)).isEmpty();
    }
}
