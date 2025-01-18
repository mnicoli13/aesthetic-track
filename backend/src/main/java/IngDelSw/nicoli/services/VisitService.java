package IngDelSw.nicoli.services;

import IngDelSw.nicoli.dto.GetVisitResponse;
import IngDelSw.nicoli.model.*;
import IngDelSw.nicoli.model.enums.VisitType;
import IngDelSw.nicoli.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class VisitService {

    private final VisitRepository visitRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final TreatmentRepository treatmentRepository;
    private final ProductVisitRepository productVisitRepository;
    private final ProductRepository productRepository;
    private final PhotosService photosService;

    public VisitService(VisitRepository visitRepository, UserRepository userRepository, TreatmentRepository treatmentRepository, PatientRepository patientRepository, ProductVisitRepository productVisitRepository, ProductRepository productRepository, PhotosService photosService) {
        this.visitRepository = visitRepository;
        this.userRepository = userRepository;
        this.treatmentRepository = treatmentRepository;
        this.patientRepository = patientRepository;
        this.productVisitRepository = productVisitRepository;
        this.productRepository = productRepository;

        this.photosService = photosService;
    }

    public Visit storeVisit(Integer patientId, Integer treatmentId, Integer medicId, LocalDate date, VisitType visitType, Integer localizationId, String notes) {

        // Crea una nuova visita
        Visit visit = new Visit();
        visit.setPatientId(patientId);
        visit.setTreatmentId(treatmentId);
        visit.setMedicId(medicId);
        visit.setDate(date);
        visit.setVisitType(visitType);
        visit.setLocalizationId(localizationId);
        visit.setNotes(notes);

        return visitRepository.save(visit);
    }

    public GetVisitResponse getVisit(Integer id) {
        Visit visit = visitRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Visit not found with id " + id));

        List<ProductVisit> productVisits = productVisitRepository.findByVisitId(visit.getId());

        List<Product> products = new ArrayList<>();

        if(productVisits != null && !productVisits.isEmpty()) {
            for(ProductVisit productVisit : productVisits) {
                Product product = productVisit.getProduct();
                products.add(product);
            }
        }

        GetVisitResponse response = new GetVisitResponse(visit);
        response.setProducts(products);

        List<Photo> photos = photosService.getPhotosByVisitId(visit.getId());
        response.setPhotos(photos);

        return response;
    }

    public Visit updateVisit(Integer id, Integer patientId, Integer treatmentId, Integer medicId, LocalDate date, VisitType visitType, Integer localizationId, String notes) {
        // Verifica se il paziente esiste
        Visit visit = visitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Visit not found"));

        // Aggiorna i campi desiderati
        visit.setPatientId(patientId != null ? patientId : visit.getPatientId());
        visit.setTreatmentId(treatmentId != null ? treatmentId : visit.getTreatmentId());
        visit.setMedicId(medicId != null ? medicId : visit.getMedicId());
        visit.setDate(date != null ? date : visit.getDate());
        visit.setVisitType(visitType != null ? visitType : visit.getVisitType());
        visit.setLocalizationId(localizationId != null ? localizationId : visit.getLocalizationId());
        visit.setNotes(notes != null ? notes : visit.getNotes());

        return visitRepository.save(visit);
    }

    public void deleteVisit(Integer visitId) {

        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new IllegalArgumentException("Visit not found"));

        visitRepository.delete(visit);
    }

    public List<VisitType> getVisitTypes() {
        return Arrays.asList(VisitType.values());
    }

    public Visit saveVisit(Integer treatmentId, LocalDate date, VisitType visitType, String notes, List<Integer> productIds) {

        Treatment treatment = treatmentRepository.findById(treatmentId)
                .orElseThrow(() -> new IllegalArgumentException("treatment not found"));

        Integer patientId = treatment.getPatientId();

        Integer localizationId = treatment.getLocalizationId();

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("patient not found"));

        Integer medicId = patient.getCreatedBy();

        // Crea una nuova visita
        Visit visit = new Visit();
        visit.setPatientId(patientId);
        visit.setTreatmentId(treatmentId);
        visit.setMedicId(medicId);
        visit.setDate(date);
        visit.setVisitType(visitType);
        visit.setLocalizationId(localizationId);
        visit.setNotes(notes);

        Visit savedVisit = visitRepository.save(visit);

        // Assegna i prodotti alla visita
        if (productIds != null && !productIds.isEmpty()) {
            for (Integer productId : productIds) {

                Product prod = productRepository.findById(productId)
                        .orElseThrow(() -> new IllegalArgumentException("product not found"));

                ProductVisit productsVisits = new ProductVisit();
                productsVisits.setVisit(savedVisit);
                productsVisits.setProduct(prod);

                productVisitRepository.save(productsVisits);
            }
        }

        return savedVisit;
    }

    public List<Integer> getVisitIdsByPatientId(Integer id) {
        List<Visit> visits = visitRepository.findByPatientId(id);

        List<Integer> visitIds = new ArrayList<>();

        if(visits != null && !visits.isEmpty()) {
            for (Visit visit : visits) {
                visitIds.add(visit.getId());
            }
        }
        return visitIds;
    }




}

