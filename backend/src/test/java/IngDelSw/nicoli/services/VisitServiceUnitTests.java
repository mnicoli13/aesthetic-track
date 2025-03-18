package IngDelSw.nicoli.services;

import IngDelSw.nicoli.dto.GetVisitResponse;
import IngDelSw.nicoli.model.*;
import IngDelSw.nicoli.model.enums.VisitType;
import IngDelSw.nicoli.repository.*;
import IngDelSw.nicoli.services.PhotosService;
import IngDelSw.nicoli.services.VisitService;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import({VisitServiceUnitTests.TestConfig.class, VisitService.class})
public class VisitServiceUnitTests {

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private TreatmentRepository treatmentRepository;

    @Autowired
    private ProductVisitRepository productVisitRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PhotosService photosService;

    @Autowired
    private VisitService visitService;

    private static Integer visitId;

    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        public PhotosService mockPhotosService() {
            return Mockito.mock(PhotosService.class);
        }
    }

    @Test
    @DisplayName("Test 1: Save Visit Test")
    @Order(1)
    @Rollback(value = false)
    public void saveVisitTest() {
        Patient patient = new Patient();
        patient.setFirstName("Marco");
        patient.setLastName("Rossi");
        patient.setCreatedBy(1); // Assume medic ID
        patient = patientRepository.save(patient);

        Treatment treatment = new Treatment();
        treatment.setPatientId(patient.getId());
        treatment.setLocalizationId(1); // Assume localization ID
        treatment = treatmentRepository.save(treatment);

        Visit visit = visitService.storeVisit(
                patient.getId(),
                treatment.getId(),
                1, // Medic ID
                LocalDate.now(),
                VisitType.Baseline,
                1, // Localization ID
                "First visit"
        );
        visitId = visit.getId();

        System.out.println(visit);
        Assertions.assertThat(visit.getId()).isGreaterThan(0);
        Assertions.assertThat(visit.getPatientId()).isEqualTo(patient.getId());
        Assertions.assertThat(visit.getVisitType()).isEqualTo(VisitType.Baseline);
    }

    @Test
    @Order(2)
    public void getVisitTest() {
        GetVisitResponse visit = visitService.getVisit(visitId);

        System.out.println(visit);
        Assertions.assertThat(visit.getId()).isEqualTo(visitId);
        Assertions.assertThat(visit.getNotes()).isEqualTo("First visit");
    }

    @Test
    @Order(3)
    @Rollback(value = false)
    public void updateVisitTest() {
        Visit updatedVisit = visitService.updateVisit(
                visitId,
                null, // Keep patient ID
                null, // Keep treatment ID
                null, // Keep medic ID
                LocalDate.now().plusDays(1),
                VisitType.Controllo,
                null, // Keep localization ID
                "Updated visit notes"
        );

        System.out.println(updatedVisit);
        Assertions.assertThat(updatedVisit.getVisitType()).isEqualTo(VisitType.Controllo);
        Assertions.assertThat(updatedVisit.getNotes()).isEqualTo("Updated visit notes");
    }

    @Test
    @Order(4)
    public void getVisitIdsByPatientIdTest() {
        List<Integer> visitIds = visitService.getVisitIdsByPatientId(1); // Assuming patient ID 1

        System.out.println(visitIds);
        Assertions.assertThat(visitIds).contains(visitId);
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteVisitTest() {
        visitService.deleteVisit(visitId);

        Assertions.assertThat(visitRepository.findById(visitId)).isEmpty();
    }
}
