package IngDelSw.nicoli.repositories;

import IngDelSw.nicoli.model.Visit;
import IngDelSw.nicoli.model.enums.VisitType;
import IngDelSw.nicoli.repository.VisitRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VisitRepositoryUnitTests {

    @Autowired
    private VisitRepository visitRepository;
    private static Integer visitId;

    @Test
    @DisplayName("Test 1: Save Visit Test")
    @Order(1)
    @Rollback(value = false)
    public void saveVisitTest() {

        Visit visit = new Visit();
        visit.setPatientId(1);
        visit.setTreatmentId(1);
        visit.setMedicId(1);
        visit.setDate(LocalDate.now());
        visit.setVisitType(VisitType.Baseline);
        visit.setLocalizationId(1);
        visit.setNotes("Initial consultation");

        Visit createdVisit = visitRepository.save(visit);
        visitId = createdVisit.getId();

        System.out.println(createdVisit);
        Assertions.assertThat(createdVisit.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getVisitTest() {

        Visit visit = visitRepository.findById(visitId).get();

        System.out.println(visit);
        Assertions.assertThat(visit.getId()).isEqualTo(visitId);
    }

    @Test
    @Order(3)
    public void getVisitsByPatientIdTest() {

        List<Visit> visits = visitRepository.findByPatientId(1);  // Assuming a valid patient ID exists

        System.out.println(visits);
        Assertions.assertThat(visits.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateVisitTest() {

        Visit visit = visitRepository.findById(visitId).get();
        visit.setNotes("Updated consultation notes");
        Visit updatedVisit = visitRepository.save(visit);

        System.out.println(updatedVisit);
        Assertions.assertThat(updatedVisit.getNotes()).isEqualTo("Updated consultation notes");
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteVisitTest() {

        visitRepository.deleteById((long) visitId);

        Assertions.assertThat(visitRepository.findById(visitId)).isEmpty();
    }
}
