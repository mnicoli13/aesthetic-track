package IngDelSw.nicoli.controller;

import IngDelSw.nicoli.model.Localization;
import IngDelSw.nicoli.model.enums.Gender;
import IngDelSw.nicoli.services.LocalizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/localization")
public class LocalizationController {

    private final LocalizationService localizationService;

    public LocalizationController(LocalizationService localizationService){
        this.localizationService = localizationService;
    }

    @PostMapping("/store")
    public ResponseEntity<?> storeLocalization(@RequestBody Localization localizationRequest) {
        try {
            Localization localization = localizationService.storeLocalization(
                    localizationRequest.getName(),
                    localizationRequest.getGender(),
                    localizationRequest.getDescription()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(localization);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLocalizationById(@PathVariable int id) {
        try {
            Localization localization = localizationService.getLocalization(id);

            Map<String, Object> response = new HashMap<>();
            response.put("localization", localization);
            response.put("success", true);
            return ResponseEntity.ok(response);  //200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());  //404
        }
    }

    @PostMapping("/getLocalizationsByGender")
    public ResponseEntity<?> getLocalizationsByGender(@RequestBody Gender gender) {
        try {
            List<Localization> localizations = localizationService.getLocalizationsByGender(gender);

            Map<String, Object> response = new HashMap<>();
            response.put("localizations", localizations);
            response.put("success", true);
            return ResponseEntity.ok(response);  //200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());  //404
        }
    }

    @GetMapping("/getLocalizationByTreatmentId/{id}")
    public ResponseEntity<?> getLocalizationByTreatmentId(@PathVariable int id) {
        try {
            Localization localization = localizationService.getLocalizationByTreatmentId(id);

            Map<String, Object> response = new HashMap<>();
            response.put("localization", localization);
            response.put("success", true);
            return ResponseEntity.ok(response);  //200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());  //404
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateLocalization(@RequestBody Localization localizationRequest) {
        try {
            Localization localization = localizationService.updateLocalization(
                    localizationRequest.getId(),
                    localizationRequest.getName(),
                    localizationRequest.getGender(),
                    localizationRequest.getDescription()
            );

            Map<String, Object> response = new HashMap<>();
            response.put("localization", localization);
            response.put("success", true);
            return ResponseEntity.ok(response);   //200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());  //404
        }
    }

    @PostMapping("delete/{id}")
    public ResponseEntity<?> deleteLocalization(@PathVariable int id) {
        try {
            localizationService.deleteLocalization(id);

            return ResponseEntity.ok("patient deleted successfully");  //200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());  //404
        }
    }


}
