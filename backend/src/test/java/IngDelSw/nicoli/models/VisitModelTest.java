package IngDelSw.nicoli.models;

import IngDelSw.nicoli.model.enums.VisitType;
import IngDelSw.nicoli.model.Visit;
import org.junit.jupiter.api.*;


import java.time.LocalDate;

import static org.springframework.test.util.AssertionErrors.*;

public class VisitModelTest {

    private Visit visit;

    public VisitModelTest() {
    }

    @BeforeEach
    public void setUp() {

        visit = new Visit();
        visit.setId(1);
        visit.setPatientId(100);
        visit.setTreatmentId(200);
        visit.setMedicId(300);
        visit.setDate(LocalDate.of(2025, 1, 1));
        visit.setVisitType(VisitType.Controllo);
        visit.setLocalizationId(400);
        visit.setNotes("Initial notes");
    }

    @Test
    public void testVisitCreation() {
        assertNotNull("Visit object should not be null", visit);
        assertEquals("ID should match", Integer.valueOf(1), visit.getId());
        assertEquals("Patient ID should match", Integer.valueOf(100), visit.getPatientId());
        assertEquals("Treatment ID should match", Integer.valueOf(200), visit.getTreatmentId());
        assertEquals("Medic ID should match", Integer.valueOf(300), visit.getMedicId());
        assertEquals("Date should match", LocalDate.of(2025, 1, 1), visit.getDate());
        assertEquals("Visit Type should match", VisitType.Controllo, visit.getVisitType());
        assertEquals("Localization ID should match", Integer.valueOf(400), visit.getLocalizationId());
        assertEquals("Notes should match", "Initial notes", visit.getNotes());
    }

    @Test
    public void testSettersAndGetters() {
        visit.setId(2);
        visit.setPatientId(101);
        visit.setTreatmentId(201);
        visit.setMedicId(301);
        visit.setDate(LocalDate.of(2025, 2, 2));
        visit.setVisitType(VisitType.Controllo);
        visit.setLocalizationId(401);
        visit.setNotes("Updated notes");

        assertEquals("Updated ID should match", Integer.valueOf(2), visit.getId());
        assertEquals("Updated Patient ID should match", Integer.valueOf(101), visit.getPatientId());
        assertEquals("Updated Treatment ID should match", Integer.valueOf(201), visit.getTreatmentId());
        assertEquals("Updated Medic ID should match", Integer.valueOf(301), visit.getMedicId());
        assertEquals("Updated Date should match", LocalDate.of(2025, 2, 2), visit.getDate());
        assertEquals("Updated Visit Type should match", VisitType.Controllo, visit.getVisitType());
        assertEquals("Updated Localization ID should match", Integer.valueOf(401), visit.getLocalizationId());
        assertEquals("Updated Notes should match", "Updated notes", visit.getNotes());
    }

    @Test
    public void testEmptyConstructor() {
        Visit emptyVisit = new Visit();
        assertNotNull("Empty Visit object should not be null", emptyVisit);
        assertNull("ID should be null", emptyVisit.getId());
        assertNull("Patient ID should be null", emptyVisit.getPatientId());
        assertNull("Treatment ID should be null", emptyVisit.getTreatmentId());
        assertNull("Medic ID should be null", emptyVisit.getMedicId());
        assertNull("Date should be null", emptyVisit.getDate());
        assertNull("Visit Type should be null", emptyVisit.getVisitType());
        assertNull("Localization ID should be null", emptyVisit.getLocalizationId());
        assertNull("Notes should be null", emptyVisit.getNotes());
    }
}
