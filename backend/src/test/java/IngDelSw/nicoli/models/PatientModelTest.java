package IngDelSw.nicoli.models;

import IngDelSw.nicoli.model.Patient;
import IngDelSw.nicoli.model.enums.Gender;
import IngDelSw.nicoli.model.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class PatientModelTest {

    private Patient patient;

    @BeforeEach
    public void setUp() {
        patient = new Patient(
                "John",
                "Doe",
                "john.doe@example.com",
                "password123",
                1,
                LocalDate.of(1990, 5, 20),
                "Caucasian",
                "No previous history",
                Gender.m,
                Role.Patient,
                LocalDateTime.now()
        );
    }

    @Test
    public void testConstructor() {
        assertNotNull(patient);
        assertEquals("John", patient.getFirstName());
        assertEquals("Doe", patient.getLastName());
        assertEquals("john.doe@example.com", patient.getEmail());
        assertEquals("password123", patient.getPassword());
        assertEquals(1, patient.getCreatedBy());
        assertEquals(LocalDate.of(1990, 5, 20), patient.getDateOfBirth());
        assertEquals("Caucasian", patient.getEthnicity());
        assertEquals("No previous history", patient.getPreviousHistory());
        assertEquals(Gender.m, patient.getGender());
        assertEquals(Role.Patient, patient.getRole());
    }

    @Test
    public void testSettersAndGetters() {
        patient.setFirstName("Jane");
        assertEquals("Jane", patient.getFirstName());

        patient.setLastName("Smith");
        assertEquals("Smith", patient.getLastName());

        patient.setEmail("jane.smith@example.com");
        assertEquals("jane.smith@example.com", patient.getEmail());

        patient.setPassword("newpassword");
        assertEquals("newpassword", patient.getPassword());

        patient.setCreatedBy(2);
        assertEquals(2, patient.getCreatedBy());

        patient.setDateOfBirth(LocalDate.of(1985, 7, 15));
        assertEquals(LocalDate.of(1985, 7, 15), patient.getDateOfBirth());

        patient.setEthnicity("Asian");
        assertEquals("Asian", patient.getEthnicity());

        patient.setPreviousHistory("Allergic to penicillin");
        assertEquals("Allergic to penicillin", patient.getPreviousHistory());
    }

    @Test
    public void testNullValues() {
        patient.setFirstName(null);
        assertNull(patient.getFirstName());

        patient.setLastName(null);
        assertNull(patient.getLastName());

        patient.setEmail(null);
        assertNull(patient.getEmail());

        patient.setPassword(null);
        assertNull(patient.getPassword());

        patient.setCreatedBy(null);
        assertNull(patient.getCreatedBy());

        patient.setDateOfBirth(null);
        assertNull(patient.getDateOfBirth());

        patient.setEthnicity(null);
        assertNull(patient.getEthnicity());

        patient.setPreviousHistory(null);
        assertNull(patient.getPreviousHistory());
    }
}
