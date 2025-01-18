package IngDelSw.nicoli.dto;


public class UploadPhotoRequest {
    private Integer visitId;
    private Integer localizationViewId;
    private String photo;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getLocalizationViewId() {
        return localizationViewId;
    }

    public void setLocalizationViewId(Integer localizationViewId) {
        this.localizationViewId = localizationViewId;
    }

    public Integer getVisitId() {
        return visitId;
    }

    public void setVisitId(Integer visitId) {
        this.visitId = visitId;
    }
}
