package IngDelSw.nicoli.services;

import IngDelSw.nicoli.model.Patient;
import IngDelSw.nicoli.model.User;
import IngDelSw.nicoli.model.enums.Gender;
import IngDelSw.nicoli.model.enums.Role;
import IngDelSw.nicoli.repository.PatientRepository;
import IngDelSw.nicoli.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public PatientService(PatientRepository patientRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Patient registerPatient(String firstName, String lastName, String email, String password, Gender gender, Integer created_by, LocalDate date_of_birth, String ethnicity, String previous_history) {

        // Verifica se l'utente esiste già
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        // Codifica la password
        String encodedPassword = passwordEncoder.encode(password);

        // Crea un nuovo paziente
        Patient patient = new Patient();
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setEmail(email);
        patient.setPassword(encodedPassword);
        patient.setGender(gender);
        patient.setRole(Role.Patient);
        patient.setActivatedAt(LocalDateTime.now());
        patient.setCreatedBy(created_by);
        patient.setDateOfBirth(date_of_birth);
        patient.setEthnicity(ethnicity);
        patient.setPreviousHistory(previous_history);

        return patientRepository.save(patient);
    }

    public Patient getPatient(Integer id) {
        return patientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Patient not found with id " + id));
    }

    public List<Patient> getPatientsByDoctorId(Integer createdBy) {
        return patientRepository.findByCreatedBy(createdBy);
    }

    public Patient updatePatient(Integer patientId, String firstName, String lastName, String email, Gender gender, LocalDate dateOfBirth, String ethnicity, String previousHistory) {
        // Verifica se il paziente esiste
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        // Aggiorna i campi desiderati
        patient.setFirstName(firstName != null ? firstName : patient.getFirstName());
        patient.setLastName(lastName != null ? lastName : patient.getLastName());
        patient.setEmail(email != null ? email : patient.getEmail());
        patient.setGender(gender != null ? gender : patient.getGender());
        patient.setDateOfBirth(dateOfBirth != null ? dateOfBirth : patient.getDateOfBirth());
        patient.setEthnicity(ethnicity != null ? ethnicity : patient.getEthnicity());
        patient.setPreviousHistory(previousHistory != null ? previousHistory : patient.getPreviousHistory());

        return patientRepository.save(patient);
    }

    public void deletePatient(Integer patientId) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        User user = userRepository.findById(patient.getId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        patientRepository.delete(patient);
        userRepository.delete(user);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

}

