package IngDelSw.nicoli.model.builders;

import IngDelSw.nicoli.model.Patient;
import IngDelSw.nicoli.model.enums.Gender;
import IngDelSw.nicoli.model.enums.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PatientBuilder {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Integer created_by;
    private LocalDate date_of_birth;
    private String ethnicity;
    private String previous_history;
    private Gender gender;
    private Role role;
    private LocalDateTime activated_at;

    public PatientBuilder(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public PatientBuilder setCreatedBy(Integer created_by) {
        this.created_by = created_by;
        return this;
    }

    public PatientBuilder setDateOfBirth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
        return this;
    }

    public PatientBuilder setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
        return this;
    }

    public PatientBuilder setPreviousHistory(String previous_history) {
        this.previous_history = previous_history;
        return this;
    }

    public PatientBuilder setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public PatientBuilder setRole(Role role) {
        this.role = role;
        return this;
    }

    public PatientBuilder setActivatedAt(LocalDateTime activated_at) {
        this.activated_at = activated_at;
        return this;
    }

    public Patient build() {
        return new Patient(firstName, lastName, email, password, created_by, date_of_birth, ethnicity, previous_history, gender, role, activated_at);
    }
}
