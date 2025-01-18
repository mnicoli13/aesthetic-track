package IngDelSw.nicoli.controller;

import IngDelSw.nicoli.model.LocalizationView;
import IngDelSw.nicoli.services.LocalizationViewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/localization-view")
public class LocalizationViewsController {

    private final LocalizationViewService localizationViewService;

    public LocalizationViewsController(LocalizationViewService localizationViewService){
        this.localizationViewService = localizationViewService;
    }

    @PostMapping("/store")
    public ResponseEntity<?> storeLocalizationView(@RequestBody LocalizationView localizationViewRequest) {
        try {
            LocalizationView localizationView = localizationViewService.storeLocalizationView(
                    localizationViewRequest.getLocalizationId(),
                    localizationViewRequest.getName(),
                    localizationViewRequest.getPriority()
            );

            Map<String, Object> response = new HashMap<>();
            response.put("localizationView", localizationView);
            response.put("success", true);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLocalizationViewById(@PathVariable int id) {
        try {
            LocalizationView localizationView = localizationViewService.getLocalizationView(id);

            Map<String, Object> response = new HashMap<>();
            response.put("localizationView", localizationView);
            response.put("success", true);
            return ResponseEntity.ok(response);  //200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());  //404
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateLocalizationView(@RequestBody LocalizationView localizationViewRequest) {
        try {
            LocalizationView localizationView = localizationViewService.updateLocalizationView(
                    localizationViewRequest.getId(),
                    localizationViewRequest.getLocalizationId(),
                    localizationViewRequest.getName(),
                    localizationViewRequest.getPriority()
            );

            Map<String, Object> response = new HashMap<>();
            response.put("localizationView", localizationView);
            response.put("success", true);
            return ResponseEntity.ok(response);  //200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());  //404
        }
    }

    @PostMapping("delete/{id}")
    public ResponseEntity<?> deleteLocalizationView(@PathVariable int id) {
        try {
            localizationViewService.deleteLocalizationView(id);

            return ResponseEntity.ok("patient deleted successfully");  //200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());  //404
        }
    }

    @GetMapping("getLocalizationViewByLocalizationId/{localizationId}")
    public ResponseEntity<?> getLocalizationViewByLocalizationId(@PathVariable int localizationId) {
        try {
            List<LocalizationView> localizationViews = localizationViewService.getLocalizationViewByLocalizationId(localizationId);

            Map<String, Object> response = new HashMap<>();
            response.put("localizationViews", localizationViews);
            response.put("success", true);
            return ResponseEntity.ok(response);  //200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());  //404
        }
    }


}
