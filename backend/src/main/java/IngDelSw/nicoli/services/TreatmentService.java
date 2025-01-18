package IngDelSw.nicoli.services;

import IngDelSw.nicoli.model.Patient;
import IngDelSw.nicoli.model.Treatment;
import IngDelSw.nicoli.model.User;
import IngDelSw.nicoli.model.enums.Gender;
import IngDelSw.nicoli.model.enums.Role;
import IngDelSw.nicoli.repository.TreatmentRepository;
import IngDelSw.nicoli.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TreatmentService {

    private final TreatmentRepository treatmentRepository;
    private final UserRepository userRepository;

    public TreatmentService(TreatmentRepository treatmentRepository, UserRepository userRepository) {
        this.treatmentRepository = treatmentRepository;
        this.userRepository = userRepository;
    }

    public Treatment storeTreatment(Integer patientId, String motivationTitle, String motivationDesc, Integer localizationId) {

        // Crea un nuovo trattamento
        Treatment treatment = new Treatment();
        treatment.setPatientId(patientId);
        treatment.setMotivationTitle(motivationTitle);
        treatment.setMotivationDesc(motivationDesc);
        treatment.setLocalizationId(localizationId);

        return treatmentRepository.save(treatment);
    }

    public Treatment getTreatment(Integer id) {
        return treatmentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Treatment not found with id " + id));
    }

    public Treatment updateTreatment(Integer id, Integer patientId, String motivationTitle, String motivationDesc, Integer localizationId) {
        // Verifica se il paziente esiste
        Treatment treatment = treatmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Treatment not found"));

        // Aggiorna i campi desiderati
        treatment.setPatientId(patientId != null ? patientId : treatment.getPatientId());
        treatment.setMotivationTitle(motivationTitle != null ? motivationTitle : treatment.getMotivationTitle());
        treatment.setMotivationDesc(motivationDesc != null ? motivationDesc : treatment.getMotivationDesc());
        treatment.setLocalizationId(localizationId != null ? localizationId : treatment.getLocalizationId());

        return treatmentRepository.save(treatment);
    }

    public void deleteTreatment(Integer treatmentId) {

        Treatment treatment = treatmentRepository.findById(treatmentId)
                .orElseThrow(() -> new IllegalArgumentException("Treatment not found"));

        treatmentRepository.delete(treatment);
    }

    public List<Treatment> getTreatmentsByPatientId(Integer createdBy) {
        return treatmentRepository.findByPatientId(createdBy);
    }

}

