package IngDelSw.nicoli.controller;

import IngDelSw.nicoli.dto.GetPhotoBase64ByUrlRequest;
import IngDelSw.nicoli.dto.UploadPhotoRequest;
import IngDelSw.nicoli.model.Photo;
import IngDelSw.nicoli.model.Product;
import IngDelSw.nicoli.services.PhotosService;
import IngDelSw.nicoli.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/photo")
public class PhotoController {

    private final ProductService productService;
    private final PhotosService photosService;

    public PhotoController(ProductService productService, PhotosService photosService){
        this.productService = productService;
        this.photosService = photosService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadPhoto (@RequestBody UploadPhotoRequest uploadPhotoRequest) {
        try {
            Photo photo = photosService.save(
                    uploadPhotoRequest.getPhoto(),
                    uploadPhotoRequest.getLocalizationViewId(),
                    uploadPhotoRequest.getVisitId()
            );

            Map<String, Object> response = new HashMap<>();
            response.put("photo", photo);
            response.put("success", true);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/getPhotoBase64ByUrl")
    public ResponseEntity<?> getPhotoBase64ByUrl (@RequestBody GetPhotoBase64ByUrlRequest getPhotoBase64ByUrlRequest) {
        try {
            String photoBase64 = photosService.getPhotoBase64ByUrl(
                    getPhotoBase64ByUrlRequest.getUrl()
            );

            Map<String, Object> response = new HashMap<>();
            response.put("photo", photoBase64);
            response.put("success", true);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }



}
