package IngDelSw.nicoli.services;

import IngDelSw.nicoli.model.Localization;
import IngDelSw.nicoli.model.Treatment;
import IngDelSw.nicoli.model.enums.Gender;
import IngDelSw.nicoli.repository.LocalizationRepository;
import IngDelSw.nicoli.repository.TreatmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocalizationService {

    private final LocalizationRepository localizationRepository;
    private final TreatmentRepository treatmentRepository;

    public LocalizationService(LocalizationRepository localizationRepository, TreatmentRepository treatmentRepository) {
        this.localizationRepository = localizationRepository;
        this.treatmentRepository = treatmentRepository;
    }

    public Localization storeLocalization( String name, Gender gender, String description) {

        // Crea un nuovo trattamento
        Localization localization = new Localization();
        localization.setName(name);
        localization.setGender(gender);
        localization.setDescription(description);

        return localizationRepository.save(localization);
    }

    public Localization getLocalization(Integer id) {
        return localizationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Localization not found with id " + id));
    }

    public List<Localization> getLocalizationsByGender(Gender gender) {
        return localizationRepository.findByGender(gender);
    }

    public Localization getLocalizationByTreatmentId(Integer treatementId) {

        Treatment treatment = treatmentRepository.findById(treatementId).orElseThrow(() -> new IllegalArgumentException("Treatment not found with id " + treatementId));

        Integer localizationId = treatment.getLocalizationId();

        return localizationRepository.findById(localizationId).orElseThrow(() -> new IllegalArgumentException("Localization not found with id " + localizationId));
    }

    public Localization updateLocalization(Integer id, String name, Gender gender, String description) {
        // Verifica se il paziente esiste
        Localization localization = localizationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Localization not found"));

        // Aggiorna i campi desiderati
        localization.setId(id != null ? id : localization.getId());
        localization.setName(name != null ? name : localization.getName());
        localization.setGender(gender != null ? gender : localization.getGender());
        localization.setDescription(description != null ? description : localization.getDescription());

        return localizationRepository.save(localization);
    }

    public void deleteLocalization(Integer localizationId) {

        Localization localization = localizationRepository.findById(localizationId)
                .orElseThrow(() -> new IllegalArgumentException("Localization not found"));

        localizationRepository.delete(localization);
    }

}

