package IngDelSw.nicoli.services;

import IngDelSw.nicoli.model.LocalizationView;
import IngDelSw.nicoli.repository.LocalizationViewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalizationViewService {

    private final LocalizationViewRepository localizationViewRepository;

    public LocalizationViewService(LocalizationViewRepository localizationViewRepository) {
        this.localizationViewRepository = localizationViewRepository;
    }

    public LocalizationView storeLocalizationView(Integer localizationId, String name, Integer order) {

        // Crea un nuovo trattamento
        LocalizationView localizationView = new LocalizationView();
        localizationView.setLocalizationId(localizationId);
        localizationView.setName(name);
        localizationView.setPriority(order);

        return localizationViewRepository.save(localizationView);
    }

    public LocalizationView getLocalizationView(Integer id) {
        return localizationViewRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("LocalizationView not found with id " + id));
    }

    public LocalizationView updateLocalizationView(Integer id, Integer localizationId, String name, Integer order) {
        // Verifica se il paziente esiste
        LocalizationView localizationView = localizationViewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("LocalizationView not found"));

        // Aggiorna i campi desiderati
        localizationView.setId(id != null ? id : localizationView.getId());
        localizationView.setLocalizationId(localizationId != null ? localizationId : localizationView.getLocalizationId());
        localizationView.setName(name != null ? name : localizationView.getName());
        localizationView.setPriority(order != null ? order : localizationView.getPriority());

        return localizationViewRepository.save(localizationView);
    }

    public void deleteLocalizationView(Integer localizationViewId) {

        LocalizationView localizationView = localizationViewRepository.findById(localizationViewId)
                .orElseThrow(() -> new IllegalArgumentException("LocalizationView not found"));

        localizationViewRepository.delete(localizationView);
    }

    public List<LocalizationView> getLocalizationViewByLocalizationId(Integer localizationId) {
        return localizationViewRepository.findByLocalizationId(localizationId);
    }

}

