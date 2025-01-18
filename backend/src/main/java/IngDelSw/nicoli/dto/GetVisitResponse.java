package IngDelSw.nicoli.dto;

import IngDelSw.nicoli.model.Photo;
import IngDelSw.nicoli.model.Product;
import IngDelSw.nicoli.model.Visit;

import java.util.ArrayList;
import java.util.List;

public class GetVisitResponse extends Visit {
    private List<Photo> photos;
    private List<Product> products;

    public GetVisitResponse(Visit visit) {
        super.setId(visit.getId());
        super.setPatientId(visit.getPatientId());
        super.setTreatmentId(visit.getTreatmentId());
        super.setMedicId(visit.getMedicId());
        super.setDate(visit.getDate());
        super.setVisitType(visit.getVisitType());
        super.setLocalizationId(visit.getLocalizationId());
        super.setNotes(visit.getNotes());

        // Inizializza la lista dei prodotti
        this.products = new ArrayList<>();
        this.photos = new ArrayList<>();
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
